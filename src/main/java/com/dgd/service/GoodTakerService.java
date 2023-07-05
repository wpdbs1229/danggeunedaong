package com.dgd.service;

import com.dgd.exception.message.ApplicationErrorCode;
import com.dgd.exception.error.ApplicationException;
import com.dgd.model.dto.GoodDto;
import com.dgd.model.entity.Good;
import com.dgd.model.repo.GoodQueryRepository;
import com.dgd.model.repo.GoodRepository;
import com.dgd.model.repo.GoodViewCountRepository;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodTakerService {


    private final GoodRepository goodRepository;
    private final GoodQueryRepository goodQueryRepository;
    private final GoodViewCountRepository goodViewCountRepository;
    /**
     * 상품 상세조회
     * @param goodId
     * @return
     */
    public GoodDto.Response readPerOneGood(Long goodId){
        Good good = goodRepository.findById(goodId)
                .orElseThrow(()-> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD));

        good.getGoodViewCount().updateViewCount(good.getGoodViewCount());

        goodViewCountRepository.save(good.getGoodViewCount());
        return good.toResponseDto(good.getUser());
    }

    /**
     * 나눔 상품 검색
     * @param keyword
     * @param minLatitude
     * @param minLongitude
     * @param maxLatitude
     * @param maxLongitude
     * @param mainCategory
     * @param subCategory
     * @param status
     * @param pageable
     * @return
     */
    public Page<GoodDto.ResponseList> searchGoods(final String keyword,
                                                  final Double minLatitude,
                                                  final Double minLongitude,
                                                  final Double maxLatitude,
                                                  final Double maxLongitude,
                                                  final MainCategory mainCategory,
                                                  final SubCategory subCategory,
                                                  final Status status,
                                                  final Pageable pageable){
        Page<Good> goods = (Page<Good>) goodQueryRepository.findWithSearchConditions(keyword, minLatitude,minLongitude,maxLatitude,maxLongitude,mainCategory,subCategory,status,pageable);

        return toResponseDto(goods);
    }

    public Page<GoodDto.ResponseList> toResponseDto(Page<Good> goods){
        Page<GoodDto.ResponseList> pageResponse =
                goods.map( m ->
                        GoodDto.ResponseList.builder()
                                .goodId(m.getId())
                                .title(m.getTitle())
                                .latitude(m.getLatitude())
                                .longitude(m.getLongitude())
                                .status(m.getStatus())
                                .mainCategory(m.getMainCategory())
                                .subCategory(m.getSubCategory())
                                .location(m.getUser().getLocation())
                                .goodImages(m.getGoodImageList())
                                .build());

        return pageResponse;
    }
    public GoodDto.WholeResponseList searchCoordinate(final String keyword,
                                                      final Double minLatitude,
                                                      final Double minLongitude,
                                                      final Double maxLatitude,
                                                      final Double maxLongitude,
                                                      final MainCategory mainCategory,
                                                      final SubCategory subCategory,
                                                      final Status status
                                                 ){
        List<Good> goods = goodQueryRepository.findCoordinate(keyword, minLatitude, minLongitude, maxLatitude, maxLongitude, mainCategory, subCategory, status);

        List<GoodDto.ResponseList> response = new ArrayList<>();

        for (Good good :goods){
            response.add(good.toResponsesDto());
        }

        GoodDto.WholeResponseList wholeResponseList = GoodDto.WholeResponseList.builder()
                .responseLists(response)
                .build();
        return wholeResponseList;
    }
}
