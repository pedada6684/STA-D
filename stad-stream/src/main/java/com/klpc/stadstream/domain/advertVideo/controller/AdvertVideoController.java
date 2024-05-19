package com.klpc.stadstream.domain.advertVideo.controller;


import com.klpc.stadstream.domain.advertVideo.entity.Advert;
import com.klpc.stadstream.domain.advertVideo.repository.AdvertRepository;
import com.klpc.stadstream.domain.advertVideo.service.AdvertVideoService;
import com.klpc.stadstream.global.RedisService;
import com.klpc.stadstream.global.event.AdvertStartEvnet;
import com.klpc.stadstream.global.response.ErrorCode;
import com.klpc.stadstream.global.response.exception.CustomException;
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
@RequestMapping("/advert-video")
@Tag(name = "advertVideo 컨트롤러", description = "광고 영상 API 입니다.")
public class AdvertVideoController {

    private final AdvertVideoService advertVideoService;
    private final RedisService redisService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AdvertRepository advertRepository;

    @GetMapping("{userId}/{videoId}/{contentId}")
    @Operation(summary = "광고 스트리밍", description = "광고 스트리밍")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "광고 스트리밍 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<ResourceRegion> streamingPublicVideo(@RequestHeader HttpHeaders httpHeaders, @PathVariable Long videoId, @PathVariable Long contentId, @PathVariable Long userId){
        log.info("광고 스트리밍" + "\n" + "streamingPublicVideo : "+videoId);
        ResponseEntity<ResourceRegion> resourceRegionResponseEntity = advertVideoService.streamingAdvertVideo(httpHeaders, videoId);
        //알림서비스 연결
        boolean isFirstRequest = redisService.isFirstAdvertStreamingRequest(userId, videoId);
        if (isFirstRequest){
            Advert advert = advertRepository.findFirstByAdvertVideos_Id(videoId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
            kafkaTemplate.send("advert-watch-log", new AdvertStartEvnet(videoId, advert.getId(), userId, contentId));
        }
        return resourceRegionResponseEntity;
    }
}
