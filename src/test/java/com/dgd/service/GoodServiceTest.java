package com.dgd.service;

import com.dgd.model.dto.GoodDto;
import com.dgd.model.entity.Good;
import com.dgd.model.entity.User;
import com.dgd.model.repo.GoodRepository;

import com.dgd.model.repo.UserRepository;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class GoodServiceTest {

    @Mock
    private GoodRepository goodRepository;

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private GoodService goodService;

    @BeforeEach
    public void setUpTest(){
        goodService = new GoodService(goodRepository, userRepository);
    }
    @Test
    @DisplayName("상품정보 조회하기")
    void readPerOneGood() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <3; i++) {
            list.add("image"+(i+1));
        }
        //given
        User givenUser = User.builder()
                .id(1L)
                .userId("testUser")
                .nickName("우리집쵸비다옹")
                .password("진짜쉬운패스워드")
                .location("경기도 서울시 바주카포동")
                .latitude(36.5123)
                .longitude(123.5342)
                .profileUrl("잘생겼을까?못생겼을까?ㅋ")
                .build();

        Good givenGood = Good.builder()
                .id(1L)
                .user(givenUser)
                .mainCategory(MainCategory.CAT)
                .subCategory(SubCategory.SNACK)
                .title("쵸비간식팝니당")
                .description("우리집쵸비는살찌게아니라 털이라구요")
                .latitude(36.5123)
                .longitude(123.5342)
                .status(Status.SHARING)
                .goodImageList(list)
                .view_cnt(1L)
                .build();


        Mockito.when(goodRepository.findById(1L))
                .thenReturn(Optional.of(givenGood));

        //when
        GoodDto.Response response = goodService.readPerOneGood(1L);

        //then
        Assertions.assertEquals(response.getOfferNickName(), givenGood.getUser().getNickName());
        Assertions.assertEquals(response.getMainCategory(), givenGood.getMainCategory());
        Assertions.assertEquals(response.getSubCategory(), givenGood.getSubCategory());
        Assertions.assertEquals(response.getTitle(),givenGood.getTitle());
        Assertions.assertEquals(response.getDescription(),givenGood.getDescription());
        Assertions.assertEquals(response.getStatus(),givenGood.getStatus());
        Assertions.assertEquals(response.getGoodImageList(),givenGood.getGoodImageList());
        Assertions.assertEquals(response.getView_cnt(),givenGood.getViewCnt());


        verify(goodRepository).findById(1L);
    }
}