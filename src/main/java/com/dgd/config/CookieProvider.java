package com.dgd.config;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieProvider {

    private final String refreshTokenName = "refreshToken";
    private final long refreshTokenExpiration = 2 * 24 * 60 * 60 * 1000L;

    public void setRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
        Cookie cookie = new Cookie(refreshTokenName, refreshToken);
        cookie.setMaxAge((int)refreshTokenExpiration);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}