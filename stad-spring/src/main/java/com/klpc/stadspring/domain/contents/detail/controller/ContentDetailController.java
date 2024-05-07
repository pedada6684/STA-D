package com.klpc.stadspring.domain.contents.detail.controller;

import com.klpc.stadspring.domain.contents.bookmark.service.BookmarkedContentService;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.service.ContentConceptService;
import com.klpc.stadspring.domain.contents.detail.controller.response.GetConceptAndDetailResponse;
import com.klpc.stadspring.domain.contents.detail.controller.response.GetDetailIdAndThumbnailListResponse;
import com.klpc.stadspring.domain.contents.detail.controller.response.GetDetailIdAndThumbnailResponse;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.service.ContentDetailService;
import com.klpc.stadspring.domain.contents.detail.service.command.response.GetDetailListByConceptIdResponseCommand;
import com.klpc.stadspring.domain.contents.watched.service.WatchedContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents-detail")
@Tag(name = "콘텐츠 디테일 컨트롤러", description = "Contents Detail Controller API")
public class ContentDetailController {
    private final ContentDetailService detailService;
    private final ContentConceptService conceptService;
    private final WatchedContentService watchedContentService;
    private final BookmarkedContentService bookmarkedContentService;

    @GetMapping("/streaming/{detailId}")
    @Operation(summary = "콘텐츠 스트리밍", description = "콘텐츠 스트리밍")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "콘텐츠 스트리밍 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<ResourceRegion> streamingPublicVideo(@RequestHeader HttpHeaders httpHeaders, @PathVariable Long detailId){
        // 로그가 너무 많이 찍힘
//        log.info("콘텐츠 스트리밍" + "\n" + "streamingPublicVideo : "+detailId);

        return detailService.streamingPublicVideo(httpHeaders, detailId);
    }

    @GetMapping("/{conceptId}")
    @Operation(summary = "콘텐츠 상세 조회", description = "콘텐츠 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "콘텐츠 상세 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetConceptAndDetailResponse> getConceptAndDetail(@PathVariable Long conceptId) {
        log.info("콘텐츠 상세 조회" + "\n" + "getConceptAndDetail : " + conceptId);

        ContentConcept concept = conceptService.getContentConceptById(conceptId);
        List<GetDetailListByConceptIdResponseCommand> commandList = detailService.getDetailListByConceptId(conceptId);

        GetConceptAndDetailResponse reponse = GetConceptAndDetailResponse.from(concept, commandList);

        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/collections/watching")
    @Operation(summary = "시청 중인 영상 목록", description = "시청 중인 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시청 중인 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetDetailIdAndThumbnailListResponse> getWatchingContent(@RequestParam("userId")  Long userId) {
        log.info("시청 중인 영상 목록 조회" + "\n" + "getWatchingContent : " + userId);

        List<Long> detailIdList = watchedContentService.getWatchingContentDetailIdByUserId(userId);
        List<GetDetailIdAndThumbnailResponse> responseList = new ArrayList<>();
        for (Long aLong : detailIdList) {
            ContentDetail detail = detailService.getContentDetailById(aLong);
            ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());

            responseList.add(GetDetailIdAndThumbnailResponse.from(detail, concept));
        }
        GetDetailIdAndThumbnailListResponse response = GetDetailIdAndThumbnailListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/collections/watchingAndWatched")
    @Operation(summary = "시청한 모든 영상 목록", description = "시청한 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시청한 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetDetailIdAndThumbnailListResponse> getWatchingAndWatchedContent(@RequestParam("userId")  Long userId) {
        log.info("시청한 영상 목록 조회" + "\n" + "getWatchingAndWatchedContent : " + userId);

        List<Long> detailIdList = watchedContentService.getWatchingAndWatchedContentDetailIdByUserId(userId);
        List<GetDetailIdAndThumbnailResponse> responseList = new ArrayList<>();
        for (Long aLong : detailIdList) {
            ContentDetail detail = detailService.getContentDetailById(aLong);
            ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());

            responseList.add(GetDetailIdAndThumbnailResponse.from(detail, concept));
        }
        GetDetailIdAndThumbnailListResponse response = GetDetailIdAndThumbnailListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/collections/bookmarked")
    @Operation(summary = "북마크 영상 목록", description = "북마크 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetDetailIdAndThumbnailListResponse> getBookmarkedContent(@RequestParam("userId")  Long userId) {
        log.info("북마크 영상 목록 조회" + "\n" + "getBookmarkedContent : " + userId);

        List<Long> detailIdList = bookmarkedContentService.getDetailIdByUserId(userId);
        List<GetDetailIdAndThumbnailResponse> responseList = new ArrayList<>();
        for (Long aLong : detailIdList) {
            ContentDetail detail = detailService.getContentDetailById(aLong);
            ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());

            responseList.add(GetDetailIdAndThumbnailResponse.from(detail, concept));
        }
        GetDetailIdAndThumbnailListResponse response = GetDetailIdAndThumbnailListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/collections/popular")
    @Operation(summary = "인기 영상 목록", description = "인기 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인기 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetDetailIdAndThumbnailListResponse> getPopularContent() {
        log.info("인기 영상 목록 조회" + "\n" + "getPopularContent");

        List<ContentDetail> popularList = detailService.getPopularContent();
        List<GetDetailIdAndThumbnailResponse> responseList = new ArrayList<>();
        for (ContentDetail contentDetail : popularList) {
            ContentConcept concept = conceptService.getContentConceptById(contentDetail.getContentConceptId());

            responseList.add(GetDetailIdAndThumbnailResponse.from(contentDetail, concept));
        }
        GetDetailIdAndThumbnailListResponse response = GetDetailIdAndThumbnailListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/collections/updated")
    @Operation(summary = "최신 영상 목록", description = "최신 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최신 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetDetailIdAndThumbnailListResponse> getUpdatedContent() {
        log.info("최신 영상 목록 조회" + "\n" + "getUpdatedContent");

        List<ContentDetail> popularList = detailService.getUpdatedContent();
        List<GetDetailIdAndThumbnailResponse> responseList = new ArrayList<>();
        for (ContentDetail contentDetail : popularList) {
            ContentConcept concept = conceptService.getContentConceptById(contentDetail.getContentConceptId());

            responseList.add(GetDetailIdAndThumbnailResponse.from(contentDetail, concept));
        }
        GetDetailIdAndThumbnailListResponse response = GetDetailIdAndThumbnailListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }
}
