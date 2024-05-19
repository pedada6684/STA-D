package com.klpc.stadstream.global;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
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
    private SetOperations<String, String> setOperations;

    @Resource(name = "redisTemplateDefault")
    private ZSetOperations<String, String> zSetOperations;

    private final String NOW_CONTENT_KEY = "nowContent";
    private final String NOW_AD_KEY = "nowAd";
    private final String CONTENTS_ZSET_KEY_PREFIX = "contentDetail:playCount:";


    public boolean isFirstContentStreamingRequest(Long userId, Long contentId){
        String key = NOW_CONTENT_KEY + "/" + userId;
        Boolean isContained = setOperations.isMember(key, contentId.toString());
        if (!isContained){ // is first
            setOperations.add(key, contentId.toString());
        }
        redisTemplateDefault.expire(key, 30, TimeUnit.SECONDS);
        return !isContained;
    }

    public boolean isFirstAdvertStreamingRequest(Long userId, Long videoId){
        String key = NOW_AD_KEY + "/" + userId;
        Boolean isContained = setOperations.isMember(key, videoId.toString());
        if (!isContained){ // is first
            setOperations.add(key, videoId.toString());
        }
        redisTemplateDefault.expire(key, 15, TimeUnit.SECONDS);
        return !isContained;
    }

    public void increaseContentPlayCount(Long detailId) {
        int currentMinute = LocalTime.now().getHour() * 60 + LocalTime.now().getMinute();
        int totalQuarters = 96; // 하루를 96개의 쿼터로 표현
        int currentQuarter = currentMinute / (24 * 60 / totalQuarters); // 현재 시간에 해당하는 쿼터 계산

        for (int i = 0; i < 3 * 4; i++) {
            int quarter = (currentQuarter + i) % totalQuarters;
            zSetOperations.incrementScore(CONTENTS_ZSET_KEY_PREFIX+quarter, detailId.toString(), 12-i);
            redisTemplateDefault.expire(CONTENTS_ZSET_KEY_PREFIX+quarter, 6, TimeUnit.HOURS);
        }
    }
}