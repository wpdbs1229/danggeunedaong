package com.dgd.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.dgd.exception.ApplicationErrorCode;
import com.dgd.exception.ApplicationException;
import com.dgd.model.dto.FileDetail;
import com.dgd.model.dto.GoodDto;
import com.dgd.model.entity.Good;
import com.dgd.model.entity.GoodViewCount;
import com.dgd.model.entity.User;
import com.dgd.model.repo.*;
import com.dgd.model.type.Status;
import com.dgd.util.MultipartUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodOfferService {

    private final GoodRepository goodRepository;
    private final GoodViewCountRepository goodViewCountRepository;
    private final UserRepository userRepository;
    private final SharingApplicationRepository sharingApplicationRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final AmazonS3Client amazonS3Client;

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

        for (MultipartFile multipartFile : multipartFiles){
            FileDetail fileDetail = FileDetail.multiPartOf(multipartFile);
            String path = fileDetail.getId()+"."+fileDetail.getFormat();
            amazonS3ResourceStorage.store(fileDetail.getPath(), multipartFile);

            goodImages.add(amazonS3Client.getUrl("dgd-image-storage",path).toString());
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

    public void updateStatus(Long goodId) {
        Good good = goodRepository.findById(goodId)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD));

        good.updateStatus(good.getStatus());
        goodRepository.save(good);
    }
}
