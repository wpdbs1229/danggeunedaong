package com.dgd.model.repo;

import com.dgd.model.entity.SharingApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharingApplicationRepository extends JpaRepository<SharingApplication, Long> {
    Optional<SharingApplication> findByUserIdAndProductId(String userId, Long productId);

    boolean existsByUserIdAndProductId(String userId, Long productId);
}
