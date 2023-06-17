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
    @GetMapping("/info")
    public ResponseEntity<?> readPerOneGood(@Valid @RequestParam Long goodId) {
        var result = goodTakerService.readPerOneGood(goodId);
        return ResponseEntity.ok(result);
    }
    //상품명, 지역, 업로드 시간, 사진, 나눔중
    @GetMapping("/search/title")
    public ResponseEntity<?> searchTitle(@Valid @RequestParam String keyword,
                                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC )Pageable pageable){
        var result = goodTakerService.searchTitle(keyword, pageable);
        return ResponseEntity.ok(result);
    }
}
