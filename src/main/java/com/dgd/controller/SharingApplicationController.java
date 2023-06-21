package com.dgd.controller;

import com.dgd.model.dto.SharingApplicationDto;
import com.dgd.service.SharingApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sharing")
public class SharingApplicationController {

    private final SharingApplicationService sharingApplicationService;

    /**
     * 나눔신청
     * @param form
     * @return
     */
    @PostMapping("/application")
    public ResponseEntity<?> saveSharingApplication(@Valid @RequestBody SharingApplicationDto.Request form) {
            sharingApplicationService.applySharing(form);
            return ResponseEntity.ok("나눔 신청이 완료되었습니다.");
    }

    /**
     * 나눔신청목록 불러오기
     * @param goodId
     * @return
     */
    @GetMapping("/application")
    public ResponseEntity<?> readSharingApplication(@RequestParam Long goodId){
        var responses = sharingApplicationService.readSharingApplicationStatus(goodId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/application")
    public ResponseEntity<?> cancelSharingApplication(@RequestParam Long sharingApplicationId){
        sharingApplicationService.cancelSharingApplication(sharingApplicationId);
        return ResponseEntity.ok("신청취소가 완료되었습니다.");
    }
}
