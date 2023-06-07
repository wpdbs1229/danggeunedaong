package com.dgd.service;

import com.dgd.model.dto.GoodDto;
import com.dgd.model.entity.Good;
import com.dgd.model.repo.GoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodService {

    private final GoodRepository goodRepository;
    public GoodDto.Response readPerOneGood(Long goodId){
        Good good = goodRepository.findById(goodId)
                .orElseThrow(()-> new IllegalArgumentException());

        return good.toResponseDto();
    }

    public void saveGood(GoodDto.Request form){
        goodRepository.save(form.toEntity());
    }

}
