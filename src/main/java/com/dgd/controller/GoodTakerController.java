package com.dgd.controller;

import com.dgd.service.GoodTakerService;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/offer/info")
    public ResponseEntity<?> readPerOneGood(@Valid @RequestParam Long goodId) {
        var response = goodTakerService.readPerOneGood(goodId);
        return ResponseEntity.ok(response);
    }
}
