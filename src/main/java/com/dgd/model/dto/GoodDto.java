package com.dgd.model.dto;

import com.dgd.model.entity.Good;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

public class GoodDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        private String offerId;
        private MainCategory mainCategory;
        private SubCategory subCategory;
        private String title;
        private String description;
        private String location;
        private Status status;
        private List<String> productImage;
        private Long view_cnt;


        public Good toEntity(){
            return Good.builder()
                    .offerId(this.offerId)
                    .mainCategory(this.mainCategory)
                    .subCategory(this.subCategory)
                    .title(this.title)
                    .description(this.title)
                    .location(this.location)
                    .status(this.status)
                    .productImage(this.productImage)
                    .view_cnt(0L)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String offerId;
        private MainCategory mainCategory;
        private SubCategory subCategory;
        private String title;
        private String description;
        private String location;
        private Status status;
        private List<String> productImage;
        private Long view_cnt;
    }
}
