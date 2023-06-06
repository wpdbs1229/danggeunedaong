package com.dgd.controller;

import com.dgd.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/good")
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;
    @GetMapping("/offer/info")
    public ResponseEntity<?> readPerOneGood(@Validated @RequestParam Long goodId) {
        try {
            goodService.readPerOneGood(goodId);
            return ResponseEntity.ok("ok");
        } catch (Exception e){
          return ResponseEntity.badRequest().body("등록되지 않은 상품입니다.");
        }
    }

}
