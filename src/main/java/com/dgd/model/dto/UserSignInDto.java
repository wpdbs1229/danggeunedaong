package com.dgd.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignInDto {
    private String userId;
    private String password;
}
