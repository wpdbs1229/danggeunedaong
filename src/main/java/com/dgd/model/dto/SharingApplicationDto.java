package com.dgd.model.dto;

import com.dgd.model.entity.Good;
import com.dgd.model.entity.SharingApplication;
import com.dgd.model.entity.User;
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
        private Long goodId;
        private String userId;
        private String content;

        public SharingApplication toEntity(Good good, User user, Double distance){
            return SharingApplication.builder()
                    .good(good)
                    .user(user)
                    .content(this.content)
                    .distance(distance)
                    .requestedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String userId;
        private String profileUrl;
        private Double distance;
        private String content;
    }
}
