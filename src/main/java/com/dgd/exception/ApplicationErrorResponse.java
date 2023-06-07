package com.dgd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationErrorResponse {
    private ApplicationErrorCode errorCode;
    private String description;
}
