package com.dgd.service.oauth;

import com.dgd.exception.error.AuthenticationException;
import com.dgd.model.entity.User;
import com.dgd.model.repo.UserRepository;
import com.dgd.model.type.Role;
import com.dgd.model.type.SocialType;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoOauthService {
    private final UserRepository userRepository;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String kakaoAuthorizationGrantType;

    @Value("${spring.security.oauth2.client.registration.kakao.client-authentication-method}")
    private String kakaoClientAuthenticationMethod;

    @Value("${spring.security.oauth2.client.registration.kakao.client-name}")
    private String kakaoClientName;

    @Value("${spring.security.oauth2.client.registration.kakao.scope[0]}")
    private String kakaoScope1;

    @Value("${spring.security.oauth2.client.registration.kakao.scope[1]}")
    private String kakaoScope2;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String kakaoProviderAuthorizationUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoProviderTokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoProviderUserInfoUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-name-attribute}")
    private String kakaoProviderUserNameAttribute;

    public URL getKakaoLoginPage() {
        StringBuilder sb = new StringBuilder();
        sb.append(kakaoProviderAuthorizationUri);
        sb.append("?client_id=" + kakaoClientId);
        sb.append("&redirect_uri=" + kakaoRedirectUri);
        sb.append("&response_type=code");

        try {
            URL url = new URL(sb.toString());
            return url;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getKakaoAccessToken(String code) {
        String accessToken = "";
        String requestURL = kakaoProviderTokenUri;

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            connection.setRequestMethod(kakaoClientAuthenticationMethod);
            connection.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=" + kakaoAuthorizationGrantType);
            sb.append("&client_id=" + kakaoClientId);
            sb.append("&redirect_uri=" + kakaoRedirectUri);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = connection.getResponseCode();

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    public void createKakaoUser(String token) throws AuthenticationException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(kakaoClientAuthenticationMethod);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String nickName = "";
            if(hasEmail){
                nickName = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
            }

            System.out.println("id : " + id);
            System.out.println("nickName : " + nickName);

            User user = saveUser(String.valueOf(id), nickName);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User saveUser(String id, String nickName) {
        User user = User.builder()
                .userId(id)
                .password(UUID.randomUUID().toString())
                .nickName(nickName)
                .socialType(SocialType.KAKAO)
                .role(Role.GUEST)
                .build();
        return userRepository.save(user);
    }
}