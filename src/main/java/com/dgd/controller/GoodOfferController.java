package com.dgd.controller;

import com.dgd.model.dto.GoodDto;
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
     *
     * @param goodId
     * @return
     */
    @GetMapping("/info/{goodid}")
    public ResponseEntity<?> readGoodInfo(@PathVariable(name = "goodid") Long goodId) {
        var result = goodOfferService.readGoodInfo(goodId);
        return ResponseEntity.ok(result);
    }

    /**
     * 상품 등록 (offer)
     * @param request
     * @return
     */
    @PostMapping("/info")
    public ResponseEntity<?> saveGood(@Valid @RequestPart(value = "request") GoodDto.Request request,
                                      @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles){
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
    public ResponseEntity<?> updateGood(@Valid @RequestPart(value = "request") GoodDto.UpdateRequest request,
                                        @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles){
        goodOfferService.updateGoods(request,multipartFiles);
        return ResponseEntity.ok("수정이 완료되었습니다 :)");
    }

    /**
     * 나눔 상태 변경
     * @param goodId
     * @return
     */
    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@Valid @RequestParam Long goodId){
        var result = goodOfferService.updateStatus(goodId);
        return ResponseEntity.ok(result);
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
    public ResponseEntity<?> matchUser(@RequestParam String userId,
                          @RequestParam Long goodId) {
        return ResponseEntity.ok(goodOfferService.matchUser(userId, goodId));
    }
}
