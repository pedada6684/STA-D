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
    private RedisTemplate<String, Long> redisTemplateForAdQueue;
    @Autowired
    @Qualifier("redisTemplateDefault")
    private RedisTemplate<String, String> redisTemplateDefault;

    @Resource(name = "redisTemplateForAdQueue")
    private ListOperations<String, Long> listOperations;
    @Resource(name = "redisTemplateDefault")
    private SetOperations<String, String> setOperations;

    private final String adQueueKey = "ADQUEUE";


    public void createUserAdQueue(Long userId, List<Long> advertIdListByUser){
        String key = adQueueKey+userId;
        for (Long videoUrl : advertIdListByUser) {
            listOperations.rightPush(key, videoUrl);
        }
        redisTemplateForAdQueue.expire(key, 1, TimeUnit.DAYS);
        //test
        for (Long s : listOperations.range(key, 0, -1)) {
            System.out.println("url in redis: "+s);
        }
    }

    public List<Long> popUserAdQueue(Long userId){
        String key = adQueueKey+userId;
        return listOperations.leftPop(key, 2);
    }
}