package com.klpc.stadspring.global.response.exception;

import com.klpc.stadspring.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    ErrorCode errorCode;
}