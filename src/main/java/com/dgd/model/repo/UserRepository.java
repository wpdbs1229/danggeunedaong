package com.dgd.model.repo;

import com.dgd.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String userId);
    Optional<User> findByNickName(String nickName);
}
