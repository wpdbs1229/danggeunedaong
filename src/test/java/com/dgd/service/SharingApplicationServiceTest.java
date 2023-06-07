package com.dgd.service;

import com.dgd.exception.ApplicationErrorCode;
import com.dgd.exception.ApplicationException;
import com.dgd.model.dto.SharingApplicationDto;
import com.dgd.model.entity.SharingApplication;
import com.dgd.model.repo.SharingApplicationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SharingApplicationServiceTest {


    @Autowired
    private SharingApplicationService service;

    @Autowired
    private SharingApplicationRepository sharingApplicationRepository;

    @Test
    @DisplayName("나눔 신청")
    void applySharingTest() {
        //given
        SharingApplicationDto.Request request
                = SharingApplicationDto.Request.builder()
                .userId("testuser")
                .productId(1L)
                .content("this is test")
                .location("서울 강남 어딘가")
                .build();
        //when
        service.applySharing(request);

        SharingApplication sharingApplication = sharingApplicationRepository.findByUserIdAndProductId(request.getUserId(), request.getProductId())
                .orElseThrow( () -> new RuntimeException());

        //then
        assertEquals("testuser",sharingApplication.getUserId());
        assertEquals(1L,sharingApplication.getProductId());
        assertEquals("this is test", sharingApplication.getContent());
        assertEquals("서울 강남 어딘가", sharingApplication.getLocation());
        assertEquals(LocalDateTime.now(),sharingApplication.getRequestedAt());
    }

    @Test
    @DisplayName("똑같은 유저가 한 나눔 상품에 대하여 중복 신청했을 경우")
    void duplicateApplication (){
        //given
        SharingApplicationDto.Request request1
                = SharingApplicationDto.Request.builder()
                .userId("testuser")
                .productId(1L)
                .content("this is test")
                .location("서울 강남 어딘가")
                .build();
        SharingApplicationDto.Request request2
                = SharingApplicationDto.Request.builder()
                .userId("testuser")
                .productId(1L)
                .content("this is test")
                .location("서울 강남 어딘가")
                .build();
        //when
        service.applySharing(request1);
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> service.applySharing(request2));
        //then
        assertEquals(ApplicationErrorCode.DUPLICATE_APPLICATION, exception.getErrorCode());
     }
}