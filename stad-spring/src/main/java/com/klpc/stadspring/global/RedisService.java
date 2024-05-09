package com.klpc.stadspring.global;

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
    @Qualifier("redisTemplateForAdQueue")
    private RedisTemplate<String, String> redisTemplateForAdQueue;
    @Autowired
    @Qualifier("redisTemplateDefault")
    private RedisTemplate<String, String> redisTemplateDefault;

    @Resource(name = "redisTemplateForAdQueue")
    private ListOperations<String, String> listOperations;
    @Resource(name = "redisTemplateDefault")
    private SetOperations<String, String> setOperations;

    private final String adQueueKey = "ADQUEUE";
    private final String TraceSteamingRequest = "TSR";


    public void createUserAdQueue(Long userId, List<String> videoUrlListByUser){
        String key = adQueueKey+userId;
        for (String videoUrl : videoUrlListByUser) {
            listOperations.rightPush(key, videoUrl);
        }
        redisTemplateForAdQueue.expire(key, 1, TimeUnit.DAYS);
        //test
        for (String s : listOperations.range(key, 0, -1)) {
            System.out.println("url in redis: "+s);
        }
    }

    public List<String> popUserAdQueue(Long userId){
        String key = adQueueKey+userId;
        return listOperations.leftPop(key, 2);
    }

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