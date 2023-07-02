package com.dgd.model.repo;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dgd.util.MultipartUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class AmazonS3ResourceStorage {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    public void goodImageStore(String fullPath, MultipartFile multipartFile) {
        String path = MultipartUtil.getLocalHomeDirectory()+File.separator+fullPath; // mac = /
        File file = new File(path);

        System.out.println(path);
        try {
            multipartFile.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, "good/"+fullPath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new IllegalArgumentException("MultiPart -> File 전환 실패 ");
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }
    public void userImageStore(String fullPath, MultipartFile multipartFile) {
        String path = MultipartUtil.getLocalHomeDirectory()+"\\"+fullPath;
        File file = new File(path);

        System.out.println(path);
        try {
            multipartFile.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, "user/"+fullPath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new IllegalArgumentException("MultiPart -> File 전환 실패 ");
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
