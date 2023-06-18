package com.dgd.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import com.dgd.model.type.Role;
import com.dgd.model.type.SocialType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    private String location; // DB 저장용 지역 이름
    private Double latitude; // 위도
    private Double longitude; // 경도
    private String profileUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<SharingApplication> sharingApplications;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Good> goods;
//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
//    private List<Pet> petList;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @Enumerated(EnumType.STRING)
//    private SocialType socialType; // KAKAO, NAVER
//    private String socialId; // 소셜 아이디 ( 기본 로그인은 null )
//    private String token;

//    public void authorizeUser() { // 유저 권한 설정
//        this.role = Role.USER;
//    }
//
//    public void addPet(Pet pet) {
//        petList.add(pet);
//    }
}