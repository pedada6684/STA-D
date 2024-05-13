package com.klpc.stadstream.domain.advertVideo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertVideoService {

    /**
     * 영상 스트리밍
     * @param httpHeaders
     * @param pathStr
     * @return
     */
    public ResponseEntity<ResourceRegion> streamingAdvertVideo(HttpHeaders httpHeaders, String pathStr) {
        // 파일 존재 확인
        try {
            UrlResource video = new UrlResource(pathStr);
            // - 파일 시스템 경로에 접근하기 위해서는 "file:"로 시작
            // - HTTP 프로토콜을 통해 자원에 접근하기 위해서는 "https:"로 시작
            // - FTP를 통해서 접근하기 위해서는 "ftp:"로 시작
            ResourceRegion resourceRegion;
            long chunkSize = 1024 * 1024;
            long contentLength = video.contentLength();

            Optional<HttpRange> optional = httpHeaders.getRange().stream().findFirst();
            HttpRange httpRange;
            if (optional.isPresent()) {
                log.info("ContentsService.streaming");
                httpRange = optional.get();
                long start = httpRange.getRangeStart(contentLength);
                long end = httpRange.getRangeEnd(contentLength);
                long rangeLength = Long.min(chunkSize, end - start + 1);
                resourceRegion = new ResourceRegion(video, start, rangeLength);
            } else {
                long rangeLength = Long.min(chunkSize, contentLength);
                //  chunk 사이즈로 자른 시작값부터 청크 사이즈 만큼 ResoureRegion을 return
                resourceRegion = new ResourceRegion(video, 0, rangeLength);
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES)) // 스트리밍을 위해 다운로드 받는 영상 캐시의 지속시간은 10분
                    .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    // header에 담긴 range 범위만큼 return
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes") // 데이터 타입은 bytes라고 헤더에 입력
                    .body(resourceRegion); // body에 가공한 데이터를 담아서 리턴
            // 영상이 재생됨에 따라 해당 url로 다시 request를 요청하여 영상정보를 받아와서 재생
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
