package com.dgd.model.dto;

import com.dgd.model.type.PetGender;
import com.dgd.model.type.PetSize;
import com.dgd.model.type.MainCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPetDto {
    private String petName;
    private MainCategory petType;
    private PetSize petSize;
    private PetGender petGender;
    private LocalDate petBirth;
    private Long userId;
}
