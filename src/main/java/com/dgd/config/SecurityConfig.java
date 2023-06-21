//package com.dgd.config;
//
//import com.dgd.oauth2.service.CustomOauth2UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final JwtFilter jwtFilter;
//    private final CustomOauth2UserService customOauth2UserService;
//    @Value("${oauth2.kakao.client.id}")
//    private String kakaoClientId;
//    @Value("${oauth2.kakao.client.secret}")
//    private String kakaoClientSecret;
//    @Value("${oauth2.kakao.redirect.uri}")
//    private String kakaoRedirectUri;
//    @Value("${kakao.authorization.uri}")
//    private String kakaoAuthorizationUri;
//    @Value("${kakao.token.uri}")
//    private String kakaoTokenUri;
//    @Value("${kakao.user.info.uri}")
//    private String kakaoUserInfoUri;
//    @Value("${oauth2.naver.client.id}")
//    private String naverClientId;
//    @Value("${oauth2.naver.client.secret}")
//    private String naverClientSecret;
//    @Value("${oauth2.naver.redirect.uri}")
//    private String naverRedirectUri;
//    @Value("${naver.authorization.uri}")
//    private String naverAuthorizationUri;
//    @Value("${naver.token.uri}")
//    private String naverTokenUri;
//    @Value("${naver.user.info.uri}")
//    private String naverUserInfoUri;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .formLogin().disable()
//                .httpBasic().disable()
//                .cors().disable()
//                .csrf().disable()
//                .authorizeRequests()
//                    .antMatchers("/swagger-ui/**", "/login", "/oauth2/**").permitAll()
//                .antMatchers("/user/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                ;
//
//        http
//                .oauth2Login()
//                .userInfoEndpoint().userService(customOauth2UserService)
//                .and()
//                ;
//
//
//        return http.build();
//    }
//
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
//
//}
