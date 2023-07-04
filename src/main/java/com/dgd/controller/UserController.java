package com.dgd.controller;


import com.dgd.model.dto.UpdateUserDto;
import com.dgd.model.dto.UserSignInDto;
import com.dgd.model.dto.UserSignUpDto;
import com.dgd.model.entity.User;
import com.dgd.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody @Valid UserSignUpDto dto) {

        return ResponseEntity.ok(userService.signUp(dto));
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody @Valid UserSignInDto dto, HttpServletResponse response) {
        return userService.signIn(dto, response);
    }

    @PatchMapping("/change")
    public void updateSocialUser (@RequestPart(value = "request") @Valid UpdateUserDto updateUserDto,
                                  @RequestPart(required = false, value = "file") MultipartFile multipartFile) {
        userService.updateUser(updateUserDto,multipartFile);
    }

    /**
     * Refresh Token 확인 후 유효한 토큰이면
     * Access Token 재발급 해주는 로직
     * *** Refresh Token 은 재발급 안됨 ! ( 보안 > 편의 ) ***
     */
    @GetMapping("/token")
    public ResponseEntity<String> getAccessToken(@RequestParam @Valid String accessToken) {
       return ResponseEntity.ok(userService.getAccessTokenByUser(accessToken));
    }

    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo(@RequestParam @Valid String userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @GetMapping("/payload")
    public ResponseEntity<User> getUserInfoByToken(@RequestParam @Valid String accessToken) {
        return ResponseEntity.ok(userService.getUserIdByAccessToken(accessToken));
    }

    @PostMapping("/logout")
    public void logOut(@RequestParam @Valid String accessToken, HttpServletResponse response) {
        userService.logOut(accessToken, response);
    }
}
