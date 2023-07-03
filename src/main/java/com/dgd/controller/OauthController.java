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
    public String kakaoSignUp(@RequestParam @Valid String accessToken, HttpServletResponse response) {

        return kakaoOauthService.createKakaoUser(accessToken, response);
    }

    @PostMapping("/naverLogin")
    public String naverSignUp(@RequestParam @Valid String accessToken, HttpServletResponse response) {

       return naverOauthService.createNaverUser(accessToken, response);
    }
}
