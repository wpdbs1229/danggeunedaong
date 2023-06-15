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
    public static class Response{
        private String offerNickName;
        private MainCategory mainCategory;
        private SubCategory subCategory;
        private String title;
        private String description;
        private String location;
        private Status status;
        private List<String> goodImageList;
        private Long view_cnt;
    }
}
