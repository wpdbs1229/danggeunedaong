package com.dgd.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.dgd.exception.ApplicationErrorCode;
import com.dgd.exception.ApplicationException;
import com.dgd.exception.error.AuthenticationException;
import com.dgd.exception.message.AuthErrorMessage;
import com.dgd.model.dto.AddPetDto;
import com.dgd.model.dto.FileDetail;
import com.dgd.model.entity.Good;
import com.dgd.model.entity.Pet;
import com.dgd.model.entity.User;
import com.dgd.model.repo.AmazonS3ResourceStorage;
import com.dgd.model.repo.PetRepository;
import com.dgd.model.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dgd.exception.message.AuthErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class PetService {

    @Value("${spring.s3.bucket}")
    private String bucketName;

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final AmazonS3Client amazonS3Client;
    private final S3Service s3Service;
    public Pet addPet (AddPetDto addPetDto, MultipartFile multipartFile) {


        String birthdayStr = addPetDto.getPetBirth().toString();
        LocalDate birthday = LocalDate.parse(birthdayStr);
        LocalDate now = LocalDate.now();



        int months = (int) ChronoUnit.MONTHS.between(birthday, now);
        int years = (int) ChronoUnit.YEARS.between(birthday, now);

        int petAge = 0;
        if (months < 12) {
            petAge = months;
        } else {
            petAge = years;
        }


        Pet pet = Pet.builder()
                .petName(addPetDto.getPetName())
                .petSize(addPetDto.getPetSize())
                .petType(addPetDto.getPetType())
                .petGender(addPetDto.getPetGender())
                .petSize(addPetDto.getPetSize())
                .petImage(s3Service.uploadImage(multipartFile))
                .petAge(petAge)
                .userId(addPetDto.getUserId())
                .build();

        User user = userRepository.findById(addPetDto.getUserId())
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));
        user.addPet(pet);

        return petRepository.save(pet);
    }



}
