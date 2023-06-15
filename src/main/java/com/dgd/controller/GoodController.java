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

    /**
     * 상품 상세 정보 조회 (offer)
     * @param goodId
     * @return
     */
    @GetMapping("/offer/info")
    public ResponseEntity<?> readPerOneGood(@Valid @RequestParam Long goodId) {
        var response = goodService.readPerOneGood(goodId);
        return ResponseEntity.ok(response);
    }

    /**
     * 상품 등록 (offer)
     * @param request
     * @return
     */
    @PostMapping("/offer/info")
    public ResponseEntity<?> saveGood(@RequestBody GoodDto.Request request){
        goodService.saveGood(request);
        return  ResponseEntity.ok("등록이 완료되었습니다.");
    }

    @GetMapping("/offer")
    public ResponseEntity<?> readGoods(@Valid @RequestParam String userId){
       var result = goodService.readGoods(userId);
        return ResponseEntity.ok(result);
    }


}
