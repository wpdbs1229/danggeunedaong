package com.dgd.service;

import com.dgd.model.dto.GoodDto;
import com.dgd.model.entity.Good;
import com.dgd.model.repo.GoodRepository;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
class GoodServiceTest {

    @Autowired
    private GoodService goodService;

    @Autowired
    private GoodRepository goodRepository;


    @Test
    void readPerOneGood() {
        //given
        List<String> list = new ArrayList<>();
        for (int i = 0; i <3; i++) {
            list.add("image"+(i+1));
        }



        goodRepository.save(
                Good.builder()
                        .id(1L)
                        .offerId("testUser")
                        .mainCategory(MainCategory.CAT)
                        .subCategory(SubCategory.FODDER)
                        .title("고양이 사료")
                        .description("맛있는 사료입니다.")
                        .location("대한민구 경기도 어딘가")
                        .status(Status.SHARING)
                        .productImage(list)
                        .view_cnt(5L)
                        .build());

        GoodDto.Response response = goodService.readPerOneGood(1L);

        System.out.println(response);

    }
}