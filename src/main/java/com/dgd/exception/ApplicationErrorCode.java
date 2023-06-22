package com.dgd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplicationErrorCode {

    DUPLICATE_APPLICATION(HttpStatus.BAD_REQUEST,1001, "이미 나눔을 신청하셨습니다."),
    NOT_REGISTERED_USER(HttpStatus.BAD_REQUEST,1002,"등록되지않은 사용자입니다."),
    NOT_REGISTERED_GOOD(HttpStatus.BAD_REQUEST,1003,"등록되지않은 상품입니다."),
    NOT_REGISTERED_APPLICATION(HttpStatus.BAD_REQUEST,1004,"등록되지 않은 신청입니다."),
    NOT_VALID_ADDRESS(HttpStatus.BAD_REQUEST,1005,"올바른 주소방식이 아닙니다.")
    ;
    
    private final HttpStatus status;
    private final int errorCode;
    private final String description;

}
