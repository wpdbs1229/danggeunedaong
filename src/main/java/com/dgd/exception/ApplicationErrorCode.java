package com.dgd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationErrorCode {

    DUPLICATE_APPLICATION(1001, "이미 나눔을 신청하셨습니다.");

    private final int errorCode;
    private final String description;

}
