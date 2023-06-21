//package com.dgd.controller;
//
//import com.dgd.model.dto.UserSignInDto;
//import com.dgd.model.dto.UserSignUpDto;
//import com.dgd.model.entity.User;
//import com.dgd.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/user")
//@RequiredArgsConstructor
//public class UserController {
//    private final UserService userService;
//
//    @PostMapping("/sign-up")
//    public ResponseEntity<User> signUp(@RequestBody @Valid UserSignUpDto dto) {
//        return ResponseEntity.ok(userService.signUp(dto));
//    }
//
//    @PostMapping("/sign-in")
//    public String signIn(@RequestBody @Valid UserSignInDto dto) {
//        userService.signIn(dto);
//        return "로그인";
//    }
//
//    @GetMapping("oauth2/social/kakao")
//    public @ResponseBody String kakaoCallback(String code) { // 데이터 리턴
//        return "데이터 저장 code : " + code;
//    }
//}
