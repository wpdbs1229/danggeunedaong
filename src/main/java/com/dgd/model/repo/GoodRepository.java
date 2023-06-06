package com.dgd.model.repo;

import com.dgd.model.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {

    Optional<Good> findByOfferId(Long offerId);
}
