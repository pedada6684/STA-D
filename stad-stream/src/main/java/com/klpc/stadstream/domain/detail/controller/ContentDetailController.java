package com.klpc.stadstream.domain.detail.controller;

import com.klpc.stadstream.domain.detail.service.ContentDetailService;
import com.klpc.stadstream.global.RedisService;
import com.klpc.stadstream.global.event.ContentStartEvnet;
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
        boolean isFirstRequest = redisService.isFirstStreamingRequest(userId, detailId);
        if (isFirstRequest){
            kafkaTemplate.send("content-start", new ContentStartEvnet(userId, detailId));
        }
        return resourceRegionResponseEntity;
    }
}
