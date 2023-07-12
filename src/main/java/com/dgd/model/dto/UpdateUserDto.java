package com.dgd.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDto {
    private String location; // 지도값 생성해서 넣는 dto
    private String nickName;
}
