package com.dgd.model.dto;

import com.dgd.model.entity.Good;
import com.dgd.model.entity.GoodViewCount;
import com.dgd.model.entity.User;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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





        public Good toEntity(User user, GoodViewCount viewCnt, Status status, List<String> goodImages){
            return Good.builder()
                    .user(user)
                    .mainCategory(this.mainCategory)
                    .subCategory(this.subCategory)
                    .title(this.title)
                    .description(this.description)
                    .latitude(user.getLatitude())
                    .longitude(user.getLongitude())
                    .status(status)
                    .goodImageList(goodImages)
                    .goodViewCount(viewCnt)
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

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private Long goodId;
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
    public static class MyResponseList {
        private Long goodId;
        private String title;
        private List<String> featuredImage;
        private LocalDateTime updatedAt;
        private String location;
        private Status status;
        private Integer sharingApplicationNum;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseList {
        private Long goodId;
        private String title;
        private List<String> goodImages;
        private double latitude; // 위도
        private double longitude; // 경도
        private String location;
        private Status status;
        private MainCategory mainCategory;
        private SubCategory subCategory;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WholeResponseList {
        private List<ResponseList> responseLists;
        private int totalPageNum;
    }


}
