package com.dgd.service;

import com.dgd.exception.error.ApplicationException;
import com.dgd.exception.error.AuthenticationException;
import com.dgd.exception.message.ApplicationErrorCode;
import com.dgd.model.dto.SharingApplicationDto;
import com.dgd.model.entity.Good;
import com.dgd.model.entity.SharingApplication;
import com.dgd.model.entity.User;
import com.dgd.model.repo.GoodRepository;
import com.dgd.model.repo.SharingApplicationRepository;
import com.dgd.model.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.dgd.exception.message.ApplicationErrorCode.NOT_REGISTERED_APPLICATION;
import static com.dgd.exception.message.ApplicationErrorCode.NOT_REGISTERED_GOOD;
import static com.dgd.exception.message.AuthErrorMessage.*;

@Service
@RequiredArgsConstructor
public class SharingApplicationService {

    private final SharingApplicationRepository sharingApplicationRepository;
    private final UserRepository userRepository;
    private final GoodRepository goodRepository;

    /**
     * 나눔 신청
     *
     * @param form
     */
    @Transactional
    public void applySharing(SharingApplicationDto.Request form) {
        EtcFeat dis = new EtcFeat();

        Good good = goodRepository.findById(form.getGoodId())
                .orElseThrow(() -> new ApplicationException(NOT_REGISTERED_GOOD));
        User user = userRepository.findByUserId(form.getUserId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_USER));

        boolean exists = sharingApplicationRepository.existsByUserAndGood(user, good);
        if (exists) {
            throw new ApplicationException(ApplicationErrorCode.DUPLICATE_APPLICATION);
        }
        Double takerLat = user.getLatitude();
        Double takerLon = user.getLongitude();

        Double offerLat = good.getLatitude();
        Double offerLon = good.getLongitude();

        double distance = dis.getDistance(takerLat, takerLon, offerLat, offerLon);
        sharingApplicationRepository.save(form.toEntity(good, user, distance));
    }

    /**
     * 나눔 신청 목록 불러오기
     *
     * @param goodId
     * @return
     */
    public List<SharingApplicationDto.Response> readSharingApplicationStatus(Long goodId) {

        Good good = goodRepository.findById(goodId)
                .orElseThrow(() -> new ApplicationException(NOT_REGISTERED_GOOD));
        List<SharingApplication> applications = sharingApplicationRepository.findAllByGood(good);


        List<SharingApplicationDto.Response> responses = new ArrayList<>();

        //이 부분이 궁금하네요 비어있다면,,, 어떻게 해야할까요 오류를 처리하는 건 아닌 것 같아요 ㅠㅠ
        if (applications.isEmpty()) {
            return responses;
        }


        for (SharingApplication application : applications) {


            responses.add(SharingApplicationDto.Response.builder()
                    .sharingId(application.getId())
                    .userId(application.getUser().getUserId())
                    .distance(application.getDistance())
                    .profileUrl(application.getUser().getProfileUrl())
                    .content(application.getContent())
                    .build());
        }

        return responses;
    }

    public void cancelSharingApplication(Long sharingApplicationId) {
        boolean exists = sharingApplicationRepository.existsById(sharingApplicationId);

        if (!exists) {
            throw new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_APPLICATION);
        }

        sharingApplicationRepository.deleteById(sharingApplicationId);
    }

    public List<SharingApplication> searchMyapplyList(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));

        return sharingApplicationRepository.findAllByUser(user);
    }

    public Good searchGoodByApply(Long sharingApplicationId) {

        SharingApplication sharingApplication =  sharingApplicationRepository.findById(sharingApplicationId)
                .orElseThrow(() -> new ApplicationException(NOT_REGISTERED_APPLICATION));

        return goodRepository.findById(sharingApplication.getGood().getId())
                .orElseThrow(() -> new ApplicationException(NOT_REGISTERED_GOOD));
    }
}
