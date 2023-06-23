package com.dgd.controller;

import com.dgd.model.dto.GoodDto;
import com.dgd.model.dto.MatchUserDto;
import com.dgd.service.GoodOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/good/offer")
@RequiredArgsConstructor
public class GoodOfferController {

    private final GoodOfferService goodOfferService;

    /**
     * 상품 상세 정보 조회 (offer)
     * @param goodId
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<?> readPerOneGood(@Valid @RequestParam Long goodId) {
        var result = goodOfferService.readPerOneGood(goodId);
        return ResponseEntity.ok(result);
    }

    /**
     * 상품 등록 (offer)
     * @param request
     * @return
     */
    @PostMapping("/info")
    public ResponseEntity<?> saveGood(@Valid @RequestPart(value = "request") GoodDto.Request request,
                                      @RequestPart(value = "files") List<MultipartFile> multipartFiles){
        goodOfferService.saveGood(request, multipartFiles);
        return  ResponseEntity.ok("등록이 완료되었습니다.");
    }

    /**
     * 등록한 상품 조회
     * @param userId
     * @return
     */
    @GetMapping
    public ResponseEntity<?> readGoods(@Valid @RequestParam String userId){
       var result = goodOfferService.readGoods(userId);
        return ResponseEntity.ok(result);
    }

    /**
     * 상품 정보 수정
     * @param request
     * @return
     */
    @PutMapping("/info")
    public ResponseEntity<?> updateGood(@Valid @RequestBody GoodDto.UpdateRequest request){
        goodOfferService.updateGoods(request);
        return ResponseEntity.ok("수정이 완료되었습니다 :)");
    }

    /**
     * 나눔 상태 변경
     * @param goodId
     * @return
     */
    @PatchMapping("/status")
    public ResponseEntity<?> updateStatus(@Valid @RequestParam Long goodId){
        goodOfferService.updateStatus(goodId);
        return ResponseEntity.ok("나눔 상태가 변경되었습니다.");
    }
    /**
     * 상품 삭제
     * @param goodId
     * @return
     */
    @DeleteMapping("/info")
    public ResponseEntity<?> deleteGood(@Valid @RequestParam Long goodId){
        goodOfferService.deleteGood(goodId);
        return ResponseEntity.ok("삭제가 완료되었습니다 :)");
    }


    @GetMapping("/match") // 조회하는 유저가 offer 인지, taker 인지
    public void matchUser(@RequestBody @Valid MatchUserDto matchUserDto) {
        goodOfferService.matchUser(matchUserDto);
    }
}
