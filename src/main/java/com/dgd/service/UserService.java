//package com.dgd.service;
//
//import com.dgd.exception.error.AuthenticationException;
//import com.dgd.model.dto.UserSignInDto;
//import com.dgd.model.dto.UserSignUpDto;
//import com.dgd.model.entity.Token;
//import com.dgd.model.entity.User;
//import com.dgd.model.repo.TokenRepository;
//import com.dgd.model.repo.UserRepository;
//import com.dgd.config.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import static com.dgd.exception.message.AuthErrorMessage.*;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//    private final TokenRepository tokenRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenProvider jwtTokenProvider;
//
//
//    public User signUp (UserSignUpDto signUpDto) {
//        if (userRepository.findByUserId(signUpDto.getUserId()).isPresent()) {
//            throw new AuthenticationException(ALREADY_REGISTERED);
//        }
//
//        if (userRepository.findByNickName(signUpDto.getNickName()).isPresent()) {
//            throw new AuthenticationException(DUPLICATED_NICKNAME);
//        }
//        User user = User.builder()
//                .nickName(signUpDto.getNickName())
//                .userId(signUpDto.getUserId())
//                .password(passwordEncoder.encode(signUpDto.getPassword()))
//                .location(signUpDto.getLocation())
//                .build();
//        user.authorizeUser();
//
//        return userRepository.save(user);
//    }
//
//    public void signIn (UserSignInDto signInDto) {
//        User user = userRepository.findByUserId(signInDto.getUserId())
//                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));
//
//        if(!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
//            throw new AuthenticationException(MISMATCH_PASSWORD);
//        }
//        Token token = jwtTokenProvider.receiveAccessToken(user.getUserId());
//        tokenRepository.save(token);
//    }
//
//    public String findSocialUserNickName(String socialId) { // 소셜 로그인한 사람의 소셜 이메일로 DB에서 유저의 닉네임을 검색
//        String userNickName = userRepository.findBySocialId(socialId)
//                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND)).getNickName();
//        return userNickName;
//    }
//
//
//}
