package com.dgd.controller;

import com.dgd.service.GoodTakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/good/taker")
@RequiredArgsConstructor
public class GoodTakerController {


    private final GoodTakerService goodTakerService;

    /**
     * 나눔 상품 상세 조회
     * @param goodId
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<?> readPerOneGood(@Valid @RequestParam Long goodId) {
        var result = goodTakerService.readPerOneGood(goodId);
        return ResponseEntity.ok(result);
    }

    /**
     * 상품 제목 기반 검색 추후 위, 경도, 카테고리 기반으로 변경 예정
     * @param keyword
     * @param category
     * @param pageable
     * @return
     */
    @GetMapping("/search/title")
    public ResponseEntity<?> searchTitle(@Valid @RequestParam String keyword,
                                         @RequestParam String category,
                                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC )Pageable pageable){
        var result = goodTakerService.searchTitle(keyword, pageable);
        return ResponseEntity.ok(result);
    }
}
