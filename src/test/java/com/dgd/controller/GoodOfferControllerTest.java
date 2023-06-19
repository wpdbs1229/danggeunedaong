package com.dgd.controller;

import com.dgd.model.dto.GoodDto;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import com.dgd.service.GoodOfferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoodOfferController.class)
class GoodOfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GoodOfferService goodOfferService;

    private static final Long GOODID = 1L;

    @Test
    @DisplayName("상품 정보 조회 Controller")
    void readPerOneGood() throws Exception {


        //given
        given(goodOfferService.readPerOneGood(GOODID)).willReturn(
                GoodDto.Response.builder()
                        .offerNickName("우리집 고양이 쵸비")
                        .mainCategory(MainCategory.CAT)
                        .subCategory(SubCategory.SNACK)
                        .title("쵸비간식팔아요 ㅠㅠ")
                        .description("쵸비가 살쪄서 못먹는데요 의사쌤이 먹지말래요")
                        .location("경기도 서울시 바주카포동")
                        .status(Status.SHARING)
                        .viewCnt(1L)
                        .goodImageList(anyList())
                        .build()
        );

        //then
        mockMvc.perform(
                        get("/good/offer/info?goodId=" + GOODID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offerNickName").exists())
                .andExpect(jsonPath("$.mainCategory").exists())
                .andExpect(jsonPath("$.subCategory").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.location").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.view_cnt").exists())
                .andExpect(jsonPath("$.goodImageList").exists())
                .andDo(print());


        verify(goodOfferService).readPerOneGood(GOODID);

    }
}