package com.klpc.stadstats.global;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    @Qualifier("redisTemplateDefault")
    private RedisTemplate<String, String> redisTemplateDefault;

    @Resource(name = "redisTemplateDefault")
    private ZSetOperations<String, String> zSetOperations;

    private final String ADVERT_ZSET_KEY_PREFIX = "advert:clickCount:";

    public void increaseAdvertClickCount(Long advertId) {
        int currentMinute = LocalTime.now().getHour() * 60 + LocalTime.now().getMinute();
        int totalQuarters = 96; // 하루를 96개의 쿼터로 표현
        int currentQuarter = currentMinute / (24 * 60 / totalQuarters); // 현재 시간에 해당하는 쿼터 계산

        for (int i = 0; i < 3 * 4; i++) {
            int quarter = (currentQuarter + i) % totalQuarters;
            zSetOperations.incrementScore(ADVERT_ZSET_KEY_PREFIX +quarter, advertId.toString(), 12-i);
            redisTemplateDefault.expire(ADVERT_ZSET_KEY_PREFIX +quarter, 6, TimeUnit.HOURS);
        }
    }
}