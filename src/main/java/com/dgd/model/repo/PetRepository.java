package com.dgd.model.repo;

import com.dgd.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet,Long> {
    Optional<Pet> findById(Long id);
}
