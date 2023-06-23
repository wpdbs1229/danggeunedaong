package com.dgd.service;

import com.dgd.exception.ApplicationErrorCode;
import com.dgd.exception.ApplicationException;
import com.dgd.model.dto.GoodDto;
import com.dgd.model.dto.MatchUserDto;
import com.dgd.model.entity.Good;
import com.dgd.model.entity.User;
import com.dgd.model.repo.GoodRepository;
import com.dgd.model.repo.SharingApplicationRepository;
import com.dgd.model.repo.UserRepository;
import com.dgd.model.type.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodOfferService {

    private final GoodRepository goodRepository;
    private final UserRepository userRepository;
    private final SharingApplicationRepository sharingApplicationRepository;

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
     * 상품 등록
     * @param form
     */
    @Transactional
    public void saveGood(GoodDto.Request form){
        User user = userRepository.findByUserId(form.getUserId())
                .orElseThrow( ()->new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_USER));

        Long viewCnt = 0L;
        goodRepository.save(form.toEntity(user,viewCnt, Status.SHARING));
    }

    /**
     * 등록한 상품조회
     * @param userId
     * @return
     */
    public List<GoodDto.MyResponseList> readGoods(String userId){
        User user = userRepository.findByUserId(userId)
                .orElseThrow( () -> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_USER));

        List<Good> goods = goodRepository.findAllByUser(user);

        List<GoodDto.MyResponseList> respons = new ArrayList<>();
        for (Good good : goods){
            Integer sharingApplicationNum = sharingApplicationRepository.countByGood(good);
            respons.add(good.toResponsesDto(sharingApplicationNum));
        }

        return respons;
    }

    /**
     * 등록한 상품수정
     * @param form
     */
    @Transactional
    public void updateGoods(GoodDto.UpdateRequest form){
        Good good = goodRepository.findById(form.getGoodId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD));

        good.update(form);

        goodRepository.save(good);
    }

    /**
     * 등록한 상품 삭제
     * @param goodId
     */
    @Transactional
    public void deleteGood(Long goodId){
        boolean exists = goodRepository.existsById(goodId);

        if (!exists){
            throw new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD);
        }

        goodRepository.deleteById(goodId);
    }


    public boolean matchUser(MatchUserDto matchUserDto) {
        Optional<User> user = goodRepository.findUserById(matchUserDto.getGoodId());
        return matchUserDto.getUserId().equals(user.get().getUserId());
    }
}
