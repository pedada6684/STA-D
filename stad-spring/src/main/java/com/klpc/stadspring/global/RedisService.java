package com.klpc.stadspring.global;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    @Qualifier("redisTemplateForAdQueue")
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    @Resource(name = "redisTemplateForAdQueue")
    private ListOperations<String, String> listOperations;

    private final String adQueueKey = "ADQUEUE";
    public void createUserAdQueue(Long userId, List<String> videoUrlListByUser){
        String key = adQueueKey+userId;
        for (String videoUrl : videoUrlListByUser) {
            listOperations.rightPush(key, videoUrl);
        }
        redisTemplate.expire(key, 1, TimeUnit.DAYS); //만료일 지정
        //test
        for (String s : listOperations.range(key, 0, -1)) {
            System.out.println("url in redis: "+s);
        }
    }

    public List<String> popUserAdQueue(Long userId){
        String key = adQueueKey+userId;
        return listOperations.leftPop(key, 2);
    }
}