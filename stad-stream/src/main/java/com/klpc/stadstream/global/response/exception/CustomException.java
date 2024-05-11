package com.klpc.stadstream.global.response.exception;

import com.klpc.stadstream.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    ErrorCode errorCode;
}