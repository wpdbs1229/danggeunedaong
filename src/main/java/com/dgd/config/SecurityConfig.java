package com.dgd.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .antMatchers("/user/**", "/login", "/oauth2/**", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                ;

        return http.build();
    }

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration kakaoRegistration = ClientRegistration.withRegistrationId("KAKAO")
//                .clientId(kakaoClientId)
//                .clientSecret(kakaoClientSecret)
//                .redirectUri(kakaoRedirectUri)
//                .userInfoUri(kakaoUserInfoUri)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationUri(kakaoAuthorizationUri)
//                .tokenUri(kakaoTokenUri)
//                .userNameAttributeName("id")
//                .build();
//
//        ClientRegistration naverRegistration = ClientRegistration.withRegistrationId("NAVER")
//                .clientId(naverClientId)
//                .clientSecret(naverClientSecret)
//                .redirectUri(naverRedirectUri)
//                .userInfoUri(naverUserInfoUri)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationUri(naverAuthorizationUri)
//                .tokenUri(naverTokenUri)
//                .userNameAttributeName("response")
//                .build();
//
//        return new InMemoryClientRegistrationRepository(kakaoRegistration, naverRegistration);
//    }

}
