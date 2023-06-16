package com.dgd.model.dto;

import com.dgd.model.entity.Good;
import com.dgd.model.entity.User;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GoodDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        private String userId;
        private MainCategory mainCategory;
        private SubCategory subCategory;
        private String title;
        private String description;
        private Status status;
        private List<String> goodImageList;



        public Good toEntity(User user, Long viewCnt, Status status){
            return Good.builder()
                    .user(user)
                    .mainCategory(this.mainCategory)
                    .subCategory(this.subCategory)
                    .title(this.title)
                    .description(this.description)
                    .latitude(user.getLatitude())
                    .longitude(user.getLongitude())
                    .status(status)
                    .goodImageList(this.goodImageList)
                    .viewCnt(viewCnt)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRequest{
        private Long goodId;
        private MainCategory mainCategory;
        private SubCategory subCategory;
        private String title;
        private String description;
        private Status status;
        private List<String> goodImageList;



        public Good toEntity(){
            return Good.builder()
                    .mainCategory(this.mainCategory)
                    .subCategory(this.subCategory)
                    .title(this.title)
                    .description(this.description)
                    .goodImageList(this.goodImageList)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String offerNickName;
        private MainCategory mainCategory;
        private SubCategory subCategory;
        private String title;
        private String description;
        private String location;
        private Status status;
        private List<String> goodImageList;
        private Long viewCnt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Responses{
        private String title;
        private String featuredImage;
        private LocalDateTime updatedAt;
        private String location;
        private Integer sharingApplicationNum;
    }
}
