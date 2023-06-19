package com.dgd.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignInDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}
