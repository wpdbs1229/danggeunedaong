package com.dgd.model.repo;

import com.dgd.model.entity.Good;
import com.dgd.model.entity.User;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {

    List<Good> findAllByUser(User user);

}
