package com.dgd.service.oauth;

import com.dgd.exception.error.AuthenticationException;
import com.dgd.model.dto.UserSignInDto;
import com.dgd.model.entity.User;
import com.dgd.model.repo.UserRepository;
import com.dgd.model.type.Role;
import com.dgd.model.type.SocialType;
import com.dgd.service.UserService;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class KakaoOauthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final Long refreshTokenValidTime = 2 * 24 * 60 * 60 * 1000L;

    public String createKakaoUser(String token, HttpServletResponse response) throws AuthenticationException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
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

            BigInteger id = element.getAsJsonObject().get("id").getAsBigInteger();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();

            String nickName = "";
            String picture = "";
            if(hasEmail){

                nickName = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
                picture = element.getAsJsonObject().get("properties").getAsJsonObject().get("profile_image").getAsString();
            }

            if (userRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, nickName).isEmpty()) {
                User user = saveUser(String.valueOf(id), nickName, picture);

                UserSignInDto userSignInDto = UserSignInDto.builder()
                        .userId(String.valueOf(id))
                        .password(user.getPassword())
                        .build();

                String accessToken = userService.signIn(userSignInDto, response);

                br.close();

                return user.getRoleKey() + accessToken;

            } else if (userRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, nickName).isPresent()) {
                UserSignInDto dto = UserSignInDto.builder()

                                                .userId(String.valueOf(id))
                                                .password(String.valueOf(id))
                                                .build();

                br.close();

                return userService.signIn(dto, response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User saveUser(String id, String nickName, String picture) {
        User user = User.builder()
                .userId(id)
                .password(passwordEncoder.encode(id))
                .socialId(nickName)
                .profileUrl(picture)
                .socialType(SocialType.KAKAO)
                .role(Role.GUEST)
                .build();
        return userRepository.save(user);
    }
}