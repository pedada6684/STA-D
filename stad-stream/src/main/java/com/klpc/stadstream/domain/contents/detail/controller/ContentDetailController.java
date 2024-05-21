package com.klpc.stadstream.domain.contents.detail.controller;

import com.klpc.stadstream.domain.contents.detail.controller.response.TestResponse;
import com.klpc.stadstream.domain.contents.detail.service.ContentDetailService;
import com.klpc.stadstream.global.RedisService;
import com.klpc.stadstream.global.event.ContentStartEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
@Tag(name = "콘텐츠 디테일 컨트롤러", description = "Contents Detail Controller API")
public class ContentDetailController {
    private final ContentDetailService detailService;
    private final RedisService redisService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/{userId}/{detailId}")
    @Operation(summary = "콘텐츠 스트리밍", description = "콘텐츠 스트리밍")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "콘텐츠 스트리밍 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<ResourceRegion> streamingPublicVideo(@RequestHeader HttpHeaders httpHeaders, @PathVariable Long userId, @PathVariable Long detailId){
        log.info("streamingPublicVideo: userId: "+userId + " contents: "+detailId);
        ResponseEntity<ResourceRegion> resourceRegionResponseEntity = detailService.streamingPublicVideo(httpHeaders, detailId);
        //알림서비스 연결
        boolean isFirstRequest = redisService.isFirstContentStreamingRequest(userId, detailId);
        if (isFirstRequest){
            kafkaTemplate.send("content-start", new ContentStartEvent(userId, detailId));
            redisService.increaseContentPlayCount(detailId);
        }
        return resourceRegionResponseEntity;
    }

    @GetMapping("test/{cnt}")
    @Operation(summary = "레디스 성능 테스트", description = "레디스 성능 테스트")
    public ResponseEntity<TestResponse> testStreamingPublicVideo(@PathVariable Long cnt) {
        Long userId = 1L;
        Long detailId = 1L;
        Long redisResult;
        Long nonRedisResult;
        long startTime, endTime;

        // 1
        startTime = System.currentTimeMillis();
        for (int i = 0; i < cnt; i++) {
            ResponseEntity<ResourceRegion> resourceRegionResponseEntity = detailService.testStreamingPublicVideo(detailId);
            // 알림서비스 연결
            boolean isFirstRequest = redisService.isFirstContentStreamingRequest(userId, detailId);
            if (isFirstRequest) {
                kafkaTemplate.send("content-start", new ContentStartEvent(userId, detailId));
                redisService.increaseContentPlayCount(detailId);
            }
        }
        endTime = System.currentTimeMillis();
        nonRedisResult = endTime - startTime;

        // 2
        startTime = System.currentTimeMillis();
        for (int i = 0; i < cnt; i++) {
            ResponseEntity<ResourceRegion> resourceRegionResponseEntity = detailService.test0StreamingPublicVideo(detailId);
            // 알림서비스 연결
            boolean isFirstRequest = redisService.isFirstContentStreamingRequest(userId, detailId);
            if (isFirstRequest) {
                kafkaTemplate.send("content-start", new ContentStartEvent(userId, detailId));
                redisService.increaseContentPlayCount(detailId);
            }
        }
        endTime = System.currentTimeMillis();
        redisResult = endTime - startTime;
        TestResponse testResponse = new TestResponse(redisResult, nonRedisResult);
        return ResponseEntity.ok(testResponse);
    }

}
