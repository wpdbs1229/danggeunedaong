package com.dgd.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String userId;

    @NotEmpty
    @Column(unique = true)
    private String nickName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String location;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
    // 프로필 이미지
    private String profileUrl;

}
