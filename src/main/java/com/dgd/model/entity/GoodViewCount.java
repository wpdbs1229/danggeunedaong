package com.dgd.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodViewCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodId;

    private Long viewCount;

    public void updateViewCount(GoodViewCount goodViewCount){
        this.viewCount = goodViewCount.getViewCount()+1;
    }
}
