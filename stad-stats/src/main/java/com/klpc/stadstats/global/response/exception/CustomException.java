package com.klpc.stadstats.global.response.exception;

import com.klpc.stadstats.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    ErrorCode errorCode;
}