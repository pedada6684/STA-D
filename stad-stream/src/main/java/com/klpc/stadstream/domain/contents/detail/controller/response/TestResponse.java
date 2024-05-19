package com.klpc.stadstream.domain.contents.detail.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestResponse {
    Long redisResult;
    Long nonRedisResult;
}
