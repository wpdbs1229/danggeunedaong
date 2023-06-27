package com.dgd.controller;

import com.dgd.model.dto.AddPetDto;
import com.dgd.model.entity.Pet;
import com.dgd.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    @PostMapping("/add")
    public ResponseEntity<Pet> addPet(@RequestPart AddPetDto addPetDto,
                                      @RequestPart(required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok(petService.addPet(addPetDto, multipartFile));
    }

}
