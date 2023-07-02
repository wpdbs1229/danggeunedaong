package com.dgd.exception.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationErrorResponse> handleApplicationException(ApplicationException e){
        return ApplicationErrorResponse.toResponseEntity(e.getErrorCode());
    }

}
