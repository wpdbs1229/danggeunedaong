package com.dgd.service;

import com.dgd.exception.error.AuthenticationException;
import com.dgd.exception.message.AuthErrorMessage;
import com.dgd.model.dto.AddPetDto;
import com.dgd.model.entity.Pet;
import com.dgd.model.entity.User;
import com.dgd.model.repo.PetRepository;
import com.dgd.model.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.dgd.exception.message.AuthErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public Pet addPet (AddPetDto addPetDto) {

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
                .petAge(petAge)
                .userId(addPetDto.getUserId())
                .build();

        User user = userRepository.findById(addPetDto.getUserId())
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND));
        user.addPet(pet);

        return petRepository.save(pet);
    }
}
