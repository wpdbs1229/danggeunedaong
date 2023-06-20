package com.dgd.controller;

import com.dgd.service.UserService;
import com.dgd.service.oauth.KakaoOauthService;
import com.dgd.service.oauth.NaverOauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {
    private final KakaoOauthService kakaoOauthService;
    private final NaverOauthService naverOauthService;
    private final UserService userService;


    @GetMapping("/kakao")
    public void getKakaoLoginPage() {
        kakaoOauthService.getKakaoLoginPage();
    }

    @GetMapping("/naver")
    public void getNaverLoginPage() {
        naverOauthService.getNaverLoginPage();
    }


    @ResponseBody
    @GetMapping("/kakaoLogin")
    public void kakaoSignUp(@RequestParam String code) {
        String accessToken = kakaoOauthService.getKakaoAccessToken(code);

        kakaoOauthService.createKakaoUser(accessToken);
    }

    @ResponseBody
    @GetMapping("/naverLogin")
    public void naverSignUp(@RequestParam String code) {
        String accessToken = naverOauthService.getNaverToken(code);

        naverOauthService.createNaverUser(accessToken);
    }


}
