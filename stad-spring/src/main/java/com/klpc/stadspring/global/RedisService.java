package com.klpc.stadspring.global;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.repository.ContentDetailRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private ContentDetailRepository contentDetailRepository;
    @Autowired
    private AdvertRepository advertRepository;
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
    @Resource(name = "redisTemplateDefault")
    private ZSetOperations<String, String> zSetOperations;

    private final String AD_QUEUE_KEY = "adQueue";
    private final String NOW_CONTENT_KEY = "nowContent";
    private final String CONTENTS_ZSET_KEY_PREFIX = "contentDetail:playCount:";
    private final String ADVERT_ZSET_KEY_PREFIX = "advert:clickCount:";


    public void createUserAdQueue(Long userId, List<Long> advertIdListByUser){
        String key = AD_QUEUE_KEY +userId;
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
        String key = AD_QUEUE_KEY +userId;
        return listOperations.leftPop(key, 2);
    }

    public Long findCurrentStreamingContent(Long userId){
        Set<String> members = setOperations.members(NOW_CONTENT_KEY + "/" +userId);
        String contentDetailId = members.stream().findFirst()
                .orElseThrow(()-> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return Long.parseLong(contentDetailId);
    }

    public List<ContentDetail> findPopularContents(int limit) {
        int currentMinute = LocalTime.now().getHour() * 60 + LocalTime.now().getMinute();
        int totalQuarters = 96; // 하루를 96개의 쿼터로 표현
        int currentQuarter = currentMinute / (24 * 60 / totalQuarters); // 현재 시간에 해당하는 쿼터 계산
        Set<String> hotchart = zSetOperations.reverseRange(CONTENTS_ZSET_KEY_PREFIX + currentQuarter, 0, limit - 1);
        for (String hot : hotchart) {
            log.info("findPopularContents: "+ hot);
        }
        Set<String> contentIds = zSetOperations.reverseRange(CONTENTS_ZSET_KEY_PREFIX+currentQuarter, 0, limit - 1);
        return contentIds.stream()
                .map(contentId -> contentDetailRepository.findById(Long.parseLong(contentId))
                        .orElseThrow(()-> new CustomException(ErrorCode.ENTITIY_NOT_FOUND))
                ).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Advert> findPopularAdvert(int limit) {
        int currentMinute = LocalTime.now().getHour() * 60 + LocalTime.now().getMinute();
        int totalQuarters = 96; // 하루를 96개의 쿼터로 표현
        int currentQuarter = currentMinute / (24 * 60 / totalQuarters); // 현재 시간에 해당하는 쿼터 계산
        Set<String> advertIds = zSetOperations.reverseRange(ADVERT_ZSET_KEY_PREFIX+currentQuarter, 0, limit - 1);
        return advertIds.stream()
                .map(advertId -> advertRepository.findById(Long.parseLong(advertId))
                        .orElseThrow(()-> new CustomException(ErrorCode.ENTITIY_NOT_FOUND))
                ).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}