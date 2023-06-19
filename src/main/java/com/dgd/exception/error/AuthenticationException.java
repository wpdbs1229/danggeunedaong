package com.dgd.exception.error;


import com.dgd.exception.message.AuthErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationException extends RuntimeException{
    private final AuthErrorMessage authErrorMessage;
}
