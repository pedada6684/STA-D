package com.klpc.stadspring.domain.contents.concept.controller;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.service.ContentCategoryService;
import com.klpc.stadspring.domain.contents.categoryRelationship.service.ContentCategoryRelationshipService;
import com.klpc.stadspring.domain.contents.concept.controller.response.GetConceptResponse;
import com.klpc.stadspring.domain.contents.concept.controller.response.GetContentCategoryAndConceptListResponse;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.service.ContentConceptService;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.service.ContentDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents-concept")
@Tag(name = "콘텐츠 콘셉트 컨트롤러", description = "Contents Concept Controller API")
public class ContentConceptController {
    private final ContentConceptService conceptService;
    private final ContentCategoryService categoryService;
    private final ContentCategoryRelationshipService categoryRelationshipService;
    private final ContentDetailService detailService;

    @GetMapping("/series")
    @Operation(summary = "시리즈 메인 영상 목록", description = "시리즈 메인 영상 목록")
    ResponseEntity<List<GetContentCategoryAndConceptListResponse>> getSeriesContent() {
        List<Long> seriesCategoryIdList = categoryService.getSeriesCategoriesId();
        List<GetContentCategoryAndConceptListResponse> responseList = new ArrayList<>();
        for (int i = 0; i < seriesCategoryIdList.size(); i++) {
            ContentCategory tmp = categoryService.getContentCategoryById(seriesCategoryIdList.get(i));
            List<Long> contentIdList = categoryRelationshipService.getContentIdByCategory(seriesCategoryIdList.get(i));
            List<GetConceptResponse> contentResponseList = new ArrayList<>();
            for (int j = 0; j < contentIdList.size(); j++) {
                ContentDetail detail = detailService.getContentDetailById(contentIdList.get(j));
                ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());

                contentResponseList.add(GetConceptResponse.from(concept));
            }
            responseList.add(GetContentCategoryAndConceptListResponse.from(tmp, contentResponseList));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/series/{category}")
    @Operation(summary = "시리즈 카테고리별 영상 목록", description = "시리즈 카테고리별 영상 목록")
    ResponseEntity<List<GetConceptResponse>> getSeriesContentByCategory(@PathVariable String category) {
        Long categoryId = categoryService.getIdByIsMovieAndName(false, category);
        List<Long> conceptIdList = categoryRelationshipService.getContentIdByCategory(categoryId);
        List<GetConceptResponse> responseList = new ArrayList<>();
        for (int i = 0; i < conceptIdList.size(); i++) {
            ContentConcept concept = conceptService.getContentConceptById(conceptIdList.get(i));

            responseList.add(GetConceptResponse.from(concept));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/movie")
    @Operation(summary = "영화 메인 영상 목록", description = "영화 메인 영상 목록")
    ResponseEntity<List<GetContentCategoryAndConceptListResponse>> getMovieContent() {
        List<Long> movieCategoryIdList = categoryService.getMovieCategoriesId();
        List<GetContentCategoryAndConceptListResponse> responseList = new ArrayList<>();
        for (int i = 0; i < movieCategoryIdList.size(); i++) {
            ContentCategory tmp = categoryService.getContentCategoryById(movieCategoryIdList.get(i));
            List<Long> contentIdList = categoryRelationshipService.getContentIdByCategory(movieCategoryIdList.get(i));
            List<GetConceptResponse> contentResponseList = new ArrayList<>();
            for (int j = 0; j < contentIdList.size(); j++) {
                ContentDetail detail = detailService.getContentDetailById(contentIdList.get(j));
                ContentConcept concept = conceptService.getContentConceptById(detail.getContentConceptId());

                contentResponseList.add(GetConceptResponse.from(concept));
            }
            responseList.add(GetContentCategoryAndConceptListResponse.from(tmp, contentResponseList));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/movie/{category}")
    @Operation(summary = "영화 카테고리별 영상 목록", description = "영화 카테고리별 영상 목록")
    ResponseEntity<List<GetConceptResponse>> getMovieContentByCategory(@PathVariable String category) {
        Long categoryId = categoryService.getIdByIsMovieAndName(true, category);
        List<Long> conceptIdList = categoryRelationshipService.getContentIdByCategory(categoryId);
        List<GetConceptResponse> responseList = new ArrayList<>();
        for (int i = 0; i < conceptIdList.size(); i++) {
            ContentConcept concept = conceptService.getContentConceptById(conceptIdList.get(i));

            responseList.add(GetConceptResponse.from(concept));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/search/{keyword}")
    @Operation(summary = "키워드로 검색", description = "키워드로 검색")
    ResponseEntity<List<GetConceptResponse>> getContentConceptByKeyword(@PathVariable String keyword) {
        List<ContentConcept> conceptList = conceptService.getContentConceptByKeyword(keyword);
        List<GetConceptResponse> responseList = new ArrayList<>();
        for (int i = 0; i < conceptList.size(); i++) {
            responseList.add(GetConceptResponse.from(conceptList.get(i)));
        }
        return ResponseEntity.ok(responseList);
    }
}
