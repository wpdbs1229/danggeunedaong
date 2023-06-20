package com.dgd.config;

import com.dgd.exception.error.AuthenticationException;
import com.dgd.model.entity.Token;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.dgd.exception.message.AuthErrorMessage.INVALID_TOKEN;
import static com.dgd.exception.message.AuthErrorMessage.USER_NOT_FOUND;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${spring.jwt.secret.key}")
    private String secretKey;
    @Value("${spring.jwt.access.header}")
    private String accessHeader;
    @Value("${spring.jwt.refresh.header}")
    private String refreshHeader;

    private final Long accessTokenValidTime = 1000L * 60 * 60 * 6;
    private final Long refreshTokenValidTime = 2 * 24 * 60 * 60 * 1000L; // 2 일



    public String createAccessToken(String userId, Long validTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(accessHeader)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String createRefreshToken(String userId, Long validTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(refreshHeader)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    public Token receiveAccessToken(String userId) {
        String token = createAccessToken(userId, accessTokenValidTime);
        return new Token(token, accessTokenValidTime);
    }
    
    public Token receiveRefreshToken(String userId) { // 어떤 행동을 하면 매번 토큰 갱신
        String token = createRefreshToken(userId, refreshTokenValidTime);
        return new Token(token, refreshTokenValidTime);
    }

    public String getPayload(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (AuthenticationException e) {
            throw new AuthenticationException(INVALID_TOKEN);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {

        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
        } catch (AuthenticationException e) {
            throw new AuthenticationException(USER_NOT_FOUND);
        }
    }
}
