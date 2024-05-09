package com.klpc.stadstream.global;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    @Qualifier("redisTemplateDefault")
    private RedisTemplate<String, String> redisTemplateDefault;

    @Resource(name = "redisTemplateDefault")
    private SetOperations<String, String> setOperations;

    private final String TraceSteamingRequest = "TSR";

    public boolean isFirstStreamingRequest(Long userId, Long contentId){
        String key = TraceSteamingRequest+ "/" + userId + "/" + contentId;
        Boolean isContained = setOperations.isMember(key, "T");
        if (!isContained){ // is first
            setOperations.add(key, "T");
        }
        redisTemplateDefault.expire(key, 30, TimeUnit.SECONDS);
        return !isContained;
    }
}