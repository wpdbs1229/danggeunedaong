package com.dgd.model.dto;

import com.dgd.model.entity.SharingApplication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class SharingApplicationDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long productId;
        private String userId;
        private String location;
        private String content;

        public SharingApplication toEntity(){
            return SharingApplication.builder()
                    .productId(this.productId)
                    .userId(this.userId)
                    .location(this.location)
                    .content(this.content)
                    .requestedAt(LocalDateTime.now())
                    .build();
        }
    }
}
