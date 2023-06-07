package com.dgd.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(ApplicationException.class)
    public ApplicationErrorResponse handleApplicationException(ApplicationException e){
        return new ApplicationErrorResponse(
                e.getErrorCode(),
                e.getDescription()
        );
    }
}
