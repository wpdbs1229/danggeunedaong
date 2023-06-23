package com.dgd.controller;

import com.dgd.service.oauth.KakaoOauthService;
import com.dgd.service.oauth.NaverOauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {
    private final KakaoOauthService kakaoOauthService;
    private final NaverOauthService naverOauthService;

    @PostMapping("/kakaoLogin")
    public void kakaoSignUp(@RequestParam @Valid String accessToken, HttpServletResponse response) {

        kakaoOauthService.createKakaoUser(accessToken, response);
    }

    @PostMapping("/naverLogin")
    public void naverSignUp(@RequestParam @Valid String accessToken, HttpServletResponse response) {

        naverOauthService.createNaverUser(accessToken, response);
    }
}
