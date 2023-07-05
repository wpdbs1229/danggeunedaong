package com.dgd.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.dgd.exception.error.ApplicationException;
import com.dgd.model.dto.FileDetail;
import com.dgd.model.entity.Good;
import com.dgd.model.entity.User;
import com.dgd.model.repo.AmazonS3ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.dgd.exception.message.ApplicationErrorCode.*;

@RequiredArgsConstructor
@Service
public class S3Service {

    @Value("${spring.s3.bucket}")
    private String bucketName;

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final AmazonS3Client amazonS3Client;

    /**
     * S3 이미지 한장 업로드
     * @param multipartFile
     * @return
     */
    public String uploadUserImage(MultipartFile multipartFile) {
        String petImage = "";



        FileDetail fileDetail = FileDetail.multiPartOf(multipartFile);
        String path = fileDetail.getId()+"."+fileDetail.getFormat();
        amazonS3ResourceStorage.userImageStore(fileDetail.getPath(), multipartFile);
        String dbpath = "user/"+fileDetail.getId()+"."+fileDetail.getFormat();
        petImage = String.valueOf(amazonS3Client.getUrl(bucketName,dbpath));


        return petImage;
    }

    /**
     * S3 이미지 여러장 업로드
     * @param multipartFiles
     * @return
     */
    public List<String> uploadGoodImage(List<MultipartFile> multipartFiles) {
        List<String> goodImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles){
            FileDetail fileDetail = FileDetail.multiPartOf(multipartFile);
            String path = fileDetail.getId()+"."+fileDetail.getFormat();
            amazonS3ResourceStorage.goodImageStore(path, multipartFile);
            String dbpath = "good/"+fileDetail.getId()+"."+fileDetail.getFormat();
            goodImages.add(String.valueOf(amazonS3Client.getUrl(bucketName,dbpath)));
        }

        return goodImages;
    }


    /**
     * S3 상품 이미지 삭제
     * @param good
     */
    public void deleteImage(Good good) {
        List<String> goodImageList = good.getGoodImageList();
        for(String goodImage : goodImageList){
            String keyName = goodImage.substring(58);
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucketName, keyName);
            if (isObjectExist){
                amazonS3Client.deleteObject(bucketName,keyName);
            } else{
                throw new ApplicationException(NOT_EXIST_IMAGE);
            }
        }
    }

    /**
     * S3 유저 프로필 이미지 삭제
     * @param user
     */
    public void deleteImage(User user) {
        String userProfileUrl = user.getProfileUrl();
        String keyName = userProfileUrl.substring(58);
        boolean isObjectExist = amazonS3Client.doesObjectExist(bucketName, keyName);
        if (isObjectExist){
            amazonS3Client.deleteObject(bucketName,keyName);
        } else{
            throw new ApplicationException(NOT_REGISTERED_USER);
        }


    }



}
