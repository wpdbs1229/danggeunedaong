package com.dgd.controller;

import com.dgd.model.dto.GoodDto;
import com.dgd.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/good")
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;
    @GetMapping("/offer/info")
    public ResponseEntity<?> readPerOneGood(@Valid @RequestParam Long goodId) {
        try {
           var response = goodService.readPerOneGood(goodId);
            return ResponseEntity.ok(response);
        } catch (Exception e){
          return ResponseEntity.badRequest().body("등록되지 않은 상품입니다.");
        }
    }

    /**
     * 테스트를 위한 임시코드
     * @param request
     * @return
     */
//    @PostMapping("/offer")
//    public ResponseEntity<?> saveGood(@RequestBody GoodDto.Request request){
//        goodService.saveGood(request);
//        return  ResponseEntity.ok("저장이 완료되었습니다.");
//    }

}
