package com.dgd.exception.error;

import com.dgd.exception.message.AuthErrorMessage;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(AuthErrorMessage authErrorMessage) {
        return ResponseEntity.status(authErrorMessage.getStatus())
                .body(ErrorResponse.builder()
                        .status(authErrorMessage.getStatus().value())
                        .error(authErrorMessage.getStatus().name())
                        .code(authErrorMessage.name())
                        .message(authErrorMessage.getErrorMessage())
                        .build());
    }
}
