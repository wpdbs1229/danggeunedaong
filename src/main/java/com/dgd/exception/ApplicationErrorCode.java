package com.dgd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationErrorCode {

    DUPLICATE_APPLICATION(1001, "이미 나눔을 신청하셨습니다."),
    NOT_REGISTERED_USER(1002,"등록되지않은 사용자입니다."),
    NOT_REGISTERED_GOOD(1003,"등록되지않은 상품입니다.")
    ;
    private final int errorCode;
    private final String description;

}
