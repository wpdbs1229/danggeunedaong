package com.dgd.model.repo;

import com.dgd.model.entity.Good;
import com.dgd.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GoodRepository extends JpaRepository<Good, Long> {

    List<Good> findAllByUser(User user);

}
