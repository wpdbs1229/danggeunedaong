package com.dgd.model.repo;

import com.dgd.model.entity.GoodViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodViewCountRepository extends JpaRepository<GoodViewCount,Long> {

}
