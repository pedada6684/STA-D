package com.klpc.stadspring.domain.contents.detail.controller;

import com.klpc.stadspring.domain.contents.bookmark.service.BookmarkedContentService;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.service.ContentConceptService;
import com.klpc.stadspring.domain.contents.detail.controller.response.*;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.service.ContentDetailService;
import com.klpc.stadspring.domain.contents.detail.service.command.response.GetDetailListByConceptIdResponseCommand;
import com.klpc.stadspring.domain.contents.detail.service.command.response.GetPopularContentResponseCommand;
import com.klpc.stadspring.domain.contents.detail.service.command.response.GetWatchingContentResponseCommand;
import com.klpc.stadspring.domain.contents.watched.service.WatchedContentService;
import com.klpc.stadspring.global.RedisService;
import com.klpc.stadspring.global.event.ContentStartEvnet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final RedisService redisService;

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
    ResponseEntity<GetWatchingContentResponse> getWatchingContent(@RequestParam("userId")  Long userId) {
        log.info("시청 중인 영상 목록 조회" + "\n" + "getWatchingContent : userId = " + userId);

        List<Long> detailIdList = watchedContentService.getWatchingContentDetailIdByUserId(userId);
        List<GetWatchingContentResponseCommand> responseList = new ArrayList<>();
        for (Long aLong : detailIdList) {
            ContentDetail detail = detailService.getContentDetailById(aLong);
            ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());

            responseList.add(GetWatchingContentResponseCommand.from(concept, detail));
        }
        GetWatchingContentResponse response = GetWatchingContentResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/collections/watchingAndWatched")
    @Operation(summary = "시청한 모든 영상 목록", description = "시청한 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시청한 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetThumbnailListResponse> getWatchingAndWatchedContent(@RequestParam("userId")  Long userId) {
        log.info("시청한 영상 목록 조회" + "\n" + "getWatchingAndWatchedContent : " + userId);

        List<Long> detailIdList = watchedContentService.getWatchingAndWatchedContentDetailIdByUserId(userId);
        List<GetThumbnailResponse> responseList = new ArrayList<>();
        for (Long aLong : detailIdList) {
            ContentDetail detail = detailService.getContentDetailById(aLong);
            ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());

            responseList.add(GetThumbnailResponse.from(concept));
        }
        GetThumbnailListResponse response = GetThumbnailListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/collections/bookmarked")
    @Operation(summary = "북마크 영상 목록", description = "북마크 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetThumbnailListResponse> getBookmarkedContent(@RequestParam("userId")  Long userId) {
        log.info("북마크 영상 목록 조회" + "\n" + "getBookmarkedContent : " + userId);

        List<Long> conceptIdList = bookmarkedContentService.getConceptIdByUserId(userId);
        List<GetThumbnailResponse> responseList = new ArrayList<>();
        for (Long aLong : conceptIdList) {
            ContentConcept concept = conceptService.getContentConceptById(aLong);

            responseList.add(GetThumbnailResponse.from(concept));
        }
        GetThumbnailListResponse response = GetThumbnailListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/collections/popular")
    @Operation(summary = "인기 영상 목록", description = "인기 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인기 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetPopularContentResponse> getPopularContent() {
        log.info("인기 영상 목록 조회" + "\n" + "getPopularContent");
        List<ContentDetail> popularList = redisService.findPopularContents(10);
        // 인기 영상이 없을 수 있으니 참고(서버 초기에는 재생된 영상이 10개 미만으로 인기순위 10위가 안채워질 수 있음)

        List<GetPopularContentResponseCommand> responseList = new ArrayList<>();
        for (ContentDetail contentDetail : popularList) {
            ContentConcept concept = conceptService.getContentConceptById(contentDetail.getContentConceptId());

            responseList.add(GetPopularContentResponseCommand.from(contentDetail, concept));
        }
        GetPopularContentResponse response = GetPopularContentResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-conceptId/{detailId}")
    @Operation(summary = "detailId로 conceptId 조회", description = "detailId로 conceptId 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "detailId로 conceptId 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<Long> getConceptId(@PathVariable Long detailId) {
        log.info("detailId로 conceptId 조회" + "\n" + "getUpdatedContent : " + detailId);

        Long response = detailService.getConceptId(detailId);
        return ResponseEntity.ok(response);
    }
}
