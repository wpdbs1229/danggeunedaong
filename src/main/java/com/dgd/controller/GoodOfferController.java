package com.dgd.controller;

import com.dgd.model.dto.GoodDto;
import com.dgd.service.GoodOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/good")
@RequiredArgsConstructor
public class GoodOfferController {

    private final GoodOfferService goodOfferService;

    /**
     * 상품 상세 정보 조회 (offer)
     * @param goodId
     * @return
     */
    @GetMapping("/offer/info")
    public ResponseEntity<?> readPerOneGood(@Valid @RequestParam Long goodId) {
        var response = goodOfferService.readPerOneGood(goodId);
        return ResponseEntity.ok(response);
    }

    /**
     * 상품 등록 (offer)
     * @param request
     * @return
     */
    @PostMapping("/offer/info")
    public ResponseEntity<?> saveGood(@RequestBody GoodDto.Request request){
        goodOfferService.saveGood(request);
        return  ResponseEntity.ok("등록이 완료되었습니다.");
    }

    /**
     * 등록한 상품 조회
     * @param userId
     * @return
     */
    @GetMapping("/offer")
    public ResponseEntity<?> readGoods(@Valid @RequestParam String userId){
       var result = goodOfferService.readGoods(userId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/offer/info")
    public ResponseEntity<?> updateGood(@Valid @RequestBody GoodDto.UpdateRequest request){
        goodOfferService.updateGoods(request);
        return ResponseEntity.ok("수정이 완료되었습니다 :)");
    }


}
