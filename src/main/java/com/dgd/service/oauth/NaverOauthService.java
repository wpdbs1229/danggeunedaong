package com.dgd.service.oauth;

import com.dgd.config.JwtTokenProvider;
import com.dgd.exception.error.AuthenticationException;
import com.dgd.model.entity.User;
import com.dgd.model.repo.UserRepository;
import com.dgd.model.type.Role;
import com.dgd.model.type.SocialType;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NaverOauthService {

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naverRedirectUri;

    @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
    private String naverGrantType;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUri;

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String naverAuthorazationUri;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String naverTokenUri;

    @Value("${spring.security.oauth2.client.provider.naver.user-name-attribute}")
    private String naverAttribute;


    private final String state = "daenggeun";

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public URL getNaverLoginPage() {
        StringBuilder sb = new StringBuilder();
        sb.append(naverAuthorazationUri);
        sb.append("?response_type=code");
        sb.append("&client_id=" + naverClientId);
        sb.append("&state=" + state);
        sb.append("&redirect_uri=" + naverRedirectUri);

        try {
            URL url = new URL(sb.toString());
            return url;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNaverToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String requestURL = naverTokenUri;

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=" + naverGrantType);
            sb.append("&client_id=" + naverClientId);
            sb.append("&client_secret=" + naverClientSecret);
            sb.append("&redirect_uri=" + naverRedirectUri);
            sb.append("&code=" + code);
            sb.append("&state=" + state);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = connection.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + accessToken);
            System.out.println("refresh_token : " + refreshToken);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }


    public void createNaverUser(String token) throws AuthenticationException {


        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(response.toString());

            JsonElement responseVal = element.getAsJsonObject().get("response");
            String userId = responseVal.getAsJsonObject().get("id").getAsString();
            String email = responseVal.getAsJsonObject().get("email").getAsString();

            User user = saveUser(userId, email);
            br.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public User saveUser(String userId, String email) {
        User user = User.builder()
                .userId(userId)
                .password(UUID.randomUUID().toString())
                .nickName(email)
                .socialType(SocialType.NAVER)
                .role(Role.GUEST)
                .build();
        return userRepository.save(user);
    }

    public String generateState() { // state 난수 생성기
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
