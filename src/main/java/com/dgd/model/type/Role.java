package com.dgd.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "소셜 로그인"), USER("ROLE_USER", "인증 완료");

    private final String key;
    private final String value;
}