package com.dgd.service;

import com.dgd.exception.ApplicationErrorCode;
import com.dgd.exception.ApplicationException;
import com.dgd.model.dto.GoodDto;
import com.dgd.model.entity.Good;
import com.dgd.model.entity.User;
import com.dgd.model.repo.GoodRepository;
import com.dgd.model.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodService {

    private final GoodRepository goodRepository;
//    private final UserRepository userRepository;
    public GoodDto.Response readPerOneGood(Long goodId){
        Good good = goodRepository.findById(goodId)
                .orElseThrow(()-> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD));

        return good.toResponseDto(good.getUser());
    }
    /**
     * 테스트를 위한 임시코드
     * @param form
     * @return
     */
//    public void saveGood(GoodDto.Request form){
//        User user = userRepository.findByNickName(form.getOfferNickName())
//                .orElseThrow( ()->new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_USER));
//        goodRepository.save(form.toEntity(user));
//    }

}
