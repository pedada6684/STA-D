package com.klpc.stadspring.domain.contents.detail.controller;

import com.klpc.stadspring.domain.contents.bookmark.service.BookmarkedContentService;
import com.klpc.stadspring.domain.contents.category.service.ContentCategoryService;
import com.klpc.stadspring.domain.contents.categoryRelationship.service.ContentCategoryRelationshipService;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.service.ContentConceptService;
import com.klpc.stadspring.domain.contents.detail.controller.response.GetContentConceptResponse;
import com.klpc.stadspring.domain.contents.detail.controller.response.GetDetailResponse;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.service.ContentDetailService;
import com.klpc.stadspring.domain.contents.watched.service.WatchedContentService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final ContentCategoryService categoryService;
    private final ContentCategoryRelationshipService categoryRelationshipService;
    private final WatchedContentService watchedContentService;
    private final BookmarkedContentService bookmarkedContentService;

    @GetMapping("/streaming/{detailId}")
    @Operation(summary = "콘텐츠 스트리밍", description = "콘텐츠 스트리밍")
    ResponseEntity<ResourceRegion> streamingPublicVideo(@RequestHeader HttpHeaders httpHeaders, @PathVariable Long detailId){
        return detailService.streamingPublicVideo(httpHeaders, detailId);
    }

    @GetMapping("/{detailId}")
    @Operation(summary = "콘텐츠 상세 조회", description = "콘텐츠 상세 조회")
    ResponseEntity<GetContentConceptResponse> getContentDetailAndConcept(@PathVariable Long detailId) {
        ContentDetail detail = detailService.getContentDetailById(detailId);
        ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());
        GetContentConceptResponse reponse = GetContentConceptResponse.from(concept);
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/collections/watching")
    @Operation(summary = "시청 중인 영상 목록", description = "시청 중인 영상 목록")
    ResponseEntity<List<GetDetailResponse>> getWatchingContent(@RequestParam("userId")  Long userId) {
        List<Long> detailIdList = watchedContentService.getDetailIdByUserId(userId);
        List<GetDetailResponse> responseList = new ArrayList<>();
        for (Long aLong : detailIdList) {
            ContentDetail detail = detailService.getContentDetailById(aLong);
            ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());

            responseList.add(GetDetailResponse.from(detail, concept));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/collections/bookmarked")
    @Operation(summary = "북마크 영상 목록", description = "북마크 영상 목록")
    ResponseEntity<List<GetDetailResponse>> getBookmarkedContent(@RequestParam("userId")  Long userId) {
        List<Long> detailIdList = bookmarkedContentService.getDetailIdByUserId(userId);
        List<GetDetailResponse> responseList = new ArrayList<>();
        for (Long aLong : detailIdList) {
            ContentDetail detail = detailService.getContentDetailById(aLong);
            ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());

            responseList.add(GetDetailResponse.from(detail, concept));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/collections/popular")
    @Operation(summary = "인기 영상 목록", description = "인기 영상 목록")
    ResponseEntity<List<GetDetailResponse>> getPopularContent() {
        List<ContentDetail> popularList = detailService.getPopularContent();
        List<GetDetailResponse> responseList = new ArrayList<>();
        for (int i = 0; i < popularList.size(); i++) {
            ContentConcept concept = conceptService.getContentConceptById(popularList.get(i).getContentConceptId());

            responseList.add(GetDetailResponse.from(popularList.get(i),concept));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/collections/updated")
    @Operation(summary = "최신 영상 목록", description = "최신 영상 목록")
    ResponseEntity<List<GetDetailResponse>> getUpdatedContent() {
        List<ContentDetail> popularList = detailService.getUpdatedContent();
        List<GetDetailResponse> responseList = new ArrayList<>();
        for (int i = 0; i < popularList.size(); i++) {
            ContentConcept concept = conceptService.getContentConceptById(popularList.get(i).getContentConceptId());

            responseList.add(GetDetailResponse.from(popularList.get(i),concept));
        }
        return ResponseEntity.ok(responseList);
    }
}
