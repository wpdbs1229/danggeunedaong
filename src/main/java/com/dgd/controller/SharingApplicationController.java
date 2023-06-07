package com.dgd.controller;

import com.dgd.model.dto.SharingApplicationDto;
import com.dgd.service.SharingApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sharing")
public class SharingApplicationController {

    private final SharingApplicationService sharingApplicationService;

    @PostMapping("/application")
    public ResponseEntity<?> saveSharingApplication(
            @Validated @RequestBody SharingApplicationDto.Request form) {
        try {
            sharingApplicationService.applySharing(form);
            return ResponseEntity.ok("나눔 신청이 완료되었습니다.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
