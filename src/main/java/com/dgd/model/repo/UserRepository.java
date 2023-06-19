package com.dgd.model.repo;

import com.dgd.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickName (String NickName);

    Optional<User> findByUserId (String userId);
}
