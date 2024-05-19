package com.klpc.stadstream.domain.contents.detail.service;

import com.klpc.stadstream.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadstream.domain.contents.detail.repository.ContentDetailRepository;
import com.klpc.stadstream.global.response.ErrorCode;
import com.klpc.stadstream.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentDetailService {

    private final ContentDetailRepository repository;
    private final long chunkSize = 1024 * 1024;

    /**
     * 영상 스트리밍
     * @param httpHeaders
     * @param id
     * @return
     */
    public ResponseEntity<ResourceRegion> streamingPublicVideo(HttpHeaders httpHeaders, Long id) {
        try {
            String streamVideoUrl = getStreamVideoUrl(id);
            UrlResource video = new UrlResource(streamVideoUrl);

            HttpRange httpRange = httpHeaders.getRange().stream().findFirst()
                    .orElseThrow(()-> new CustomException(ErrorCode.REQUEST_NOT_FOUND));
            long start = httpRange.getRangeStart(video.contentLength());
            long end = httpRange.getRangeEnd(video.contentLength());
            Long rangeLength = Long.min(chunkSize, end - start + 1);

            ResourceRegion resourceRegion = new ResourceRegion(video, start, rangeLength);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES)) // 스트리밍을 위해 다운로드 받는 영상 캐시의 지속시간은 10분
                    .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    // header에 담긴 range 범위만큼 return
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes") // 데이터 타입은 bytes라고 헤더에 입력
                    .body(resourceRegion); // body에 가공한 데이터를 담아서 리턴
            // 영상이 재생됨에 따라 해당 url로 다시 request를 요청하여 영상정보를 받아와서 재생
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ResourceRegion> testStreamingPublicVideo(Long id) {
        try {
            String streamVideoUrl = getTestStreamVideoUrl(id);
            UrlResource video = new UrlResource(streamVideoUrl);

            ResourceRegion resourceRegion = new ResourceRegion(video, 0, 1L);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES)) // 스트리밍을 위해 다운로드 받는 영상 캐시의 지속시간은 10분
                    .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    // header에 담긴 range 범위만큼 return
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes") // 데이터 타입은 bytes라고 헤더에 입력
                    .body(resourceRegion); // body에 가공한 데이터를 담아서 리턴
            // 영상이 재생됨에 따라 해당 url로 다시 request를 요청하여 영상정보를 받아와서 재생
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ResourceRegion> test0StreamingPublicVideo(Long id) {
        try {
            String streamVideoUrl = getStreamVideoUrl(id);
            UrlResource video = new UrlResource(streamVideoUrl);

            ResourceRegion resourceRegion = new ResourceRegion(video, 0, 1L);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES)) // 스트리밍을 위해 다운로드 받는 영상 캐시의 지속시간은 10분
                    .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    // header에 담긴 range 범위만큼 return
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes") // 데이터 타입은 bytes라고 헤더에 입력
                    .body(resourceRegion); // body에 가공한 데이터를 담아서 리턴
            // 영상이 재생됨에 따라 해당 url로 다시 request를 요청하여 영상정보를 받아와서 재생
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Cacheable(value = "getStreamVideoUrl", key = "#id", cacheManager = "StadCacheManager")
    public String getStreamVideoUrl(Long id) {
        ContentDetail contentDetail = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return contentDetail.getVideoUrl();
    }

    public String getTestStreamVideoUrl(Long id) {
        ContentDetail contentDetail = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return contentDetail.getVideoUrl();
    }
}
