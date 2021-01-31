package com.ssongk.accongbox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomJwtRuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleJwtException(CustomJwtRuntimeException e) {

        ErrorResponse response = ErrorResponse.create()
                .code(ErrorCode.INVALID_JWT_TOKEN.getCode())
                .message(ErrorCode.INVALID_JWT_TOKEN.getMessage())
                .status(ErrorCode.INVALID_JWT_TOKEN.getStatus());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}