package com.dgd.service;

import com.dgd.exception.ApplicationErrorCode;
import com.dgd.exception.ApplicationException;
import com.dgd.model.dto.GoodDto;
import com.dgd.model.entity.Good;
import com.dgd.model.repo.GoodRepository;
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
    /**
     * 상품 상세조회
     * @param goodId
     * @return
     */
    public GoodDto.Response readPerOneGood(Long goodId){
        Good good = goodRepository.findById(goodId)
                .orElseThrow(()-> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD));

        return good.toResponseDto(good.getUser());
    }

    /**
     * 제목 검색
     * @param keyword
     * @param pageable
     * @return
     */
    public List<GoodDto.ResponseList> searchTitle(String keyword, Pageable pageable){
        Page<Good> goods = goodRepository.findByTitleContaining(keyword, pageable);

        List<GoodDto.ResponseList> response = new ArrayList<>();

        for (Good good :goods){
            response.add(good.toResponsesDto());
        }
        return response;
    }
}
