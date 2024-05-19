package com.klpc.stadstats.global.response.exception;

import com.klpc.stadstats.global.response.ErrorResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseEntity> ExceptionHandler(CustomException e) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }
}
