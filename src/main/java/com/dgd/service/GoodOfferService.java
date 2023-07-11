package com.dgd.service;

import com.dgd.exception.message.ApplicationErrorCode;
import com.dgd.exception.error.ApplicationException;
import com.dgd.model.dto.GoodDto;
import com.dgd.model.entity.Good;
import com.dgd.model.entity.GoodViewCount;
import com.dgd.model.entity.User;
import com.dgd.model.repo.*;
import com.dgd.model.type.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodOfferService {

    @Value("${spring.s3.bucket}")
    private String bucketName;

    private final GoodRepository goodRepository;
    private final GoodViewCountRepository goodViewCountRepository;
    private final UserRepository userRepository;
    private final SharingApplicationRepository sharingApplicationRepository;
    private final S3Service s3Service;

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
    public void saveGood(GoodDto.Request form, List<MultipartFile> multipartFiles){
        User user = userRepository.findByUserId(form.getUserId())
                .orElseThrow( ()->new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_USER));
        List<String> goodImages = new ArrayList<>();
        if (multipartFiles != null) {
            goodImages = s3Service.uploadGoodImage(multipartFiles);
        }

        GoodViewCount goodViewCount = goodViewCountRepository.save(GoodViewCount.builder().viewCount(0L).build());
        goodRepository.save(form.toEntity(user,goodViewCount, Status.SHARING, goodImages));
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

        List<GoodDto.MyResponseList> response = new ArrayList<>();
        for (Good good : goods){
            Integer sharingApplicationNum = sharingApplicationRepository.countByGood(good);
            response.add(good.toResponsesDto(sharingApplicationNum));
        }

        return response;
    }

    /**
     * 등록한 상품수정
     * @param form
     */
    @Transactional
    public void updateGoods(GoodDto.UpdateRequest form, List<MultipartFile> multipartFiles){
        Good good = goodRepository.findById(form.getGoodId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD));

        List<String> goodImages = new ArrayList<>();
        if (multipartFiles != null){
            if (!form.getGoodImageList().isEmpty()){
                List<String> deleteToImages = good.getGoodImageList().stream().filter(
                        image -> form.getGoodImageList().stream().noneMatch(Predicate.isEqual(image))
                ).collect(Collectors.toList());

                goodImages.addAll(form.getGoodImageList());
                s3Service.updateImage(deleteToImages);
                goodImages.addAll(s3Service.uploadGoodImage(multipartFiles));
            } else {
                s3Service.deleteImage(good);
                goodImages = s3Service.uploadGoodImage(multipartFiles);
            }

        }
        good.update(form,goodImages);
        goodRepository.save(good);
    }



    /**
     * 등록한 상품 삭제
     * @param goodId
     */
    @Transactional
    public void deleteGood(Long goodId){
        Good good = goodRepository.findById(goodId)
                .orElseThrow( () -> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_USER));

        s3Service.deleteImage(good);
        goodRepository.deleteById(goodId);
    }

    /**
     * 나눔 상태 변경
     * @param goodId
     */
    public Status updateStatus(Long goodId) {
        Good good = goodRepository.findById(goodId)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD));

        Status status = good.updateStatus(good.getStatus());
        goodRepository.save(good);
        return status;
    }

    public boolean matchUser(String userId, Long goodId) {
        Good good = goodRepository.findById(goodId)
                .orElseThrow(()-> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD));
        boolean equals = userId.equals(good.getUser().getUserId());
        return equals;
    }
}
