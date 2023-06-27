package com.dgd.model.entity;

import javax.persistence.*;
import com.dgd.model.dto.GoodDto;
import com.dgd.model.dto.UpdateUserDto;
import com.dgd.model.type.Role;
import com.dgd.model.type.SocialType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private String userId;
    private String nickName;
    @NotBlank
    private String password;
    private String location; // DB 저장용 지역 이름
    private double latitude; // 위도
    private double longitude; // 경도
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId", orphanRemoval = true)
    private List<Pet> petList;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER
    private String socialId; // 소셜 아이디 ( 기본 로그인은 null )
    private String token;
    public void authorizeUser() { // 유저 권한 설정
        this.role = Role.USER;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
    public void addPet(Pet pet) {
        petList.add(pet);
    }

    private String profileUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<SharingApplication> sharingApplications;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Good> goods;

    public void update(UpdateUserDto dto, String profileUrl){
        this.location = dto.getLocation();
        this.nickName = dto.getNickName();
        this.profileUrl = profileUrl;
    }

    public void setLatAndLon (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
