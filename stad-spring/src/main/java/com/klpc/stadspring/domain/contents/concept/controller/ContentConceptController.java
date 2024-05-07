package com.klpc.stadspring.domain.contents.concept.controller;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.service.ContentCategoryService;
import com.klpc.stadspring.domain.contents.categoryRelationship.service.ContentCategoryRelationshipService;
import com.klpc.stadspring.domain.contents.concept.controller.response.*;
import com.klpc.stadspring.domain.contents.concept.entity.ContentConcept;
import com.klpc.stadspring.domain.contents.concept.service.ContentConceptService;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.service.ContentDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시리즈 메인 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetCategoryAndConceptsListResponse> getSeriesContent() {
        log.info("시리즈 메인 영상 목록 조회" + "\n" + "getSeriesContent");

        List<Long> seriesCategoryIdList = categoryService.getSeriesCategoriesId();
        List<GetCategoryAndConceptsResponse> responseList = new ArrayList<>();
        for (Long aLong : seriesCategoryIdList) {
            ContentCategory category = categoryService.getContentCategoryById(aLong);
            List<Long> contentIdList = categoryRelationshipService.getConceptIdByCategory(aLong);
            List<GetConceptResponse> contentResponseList = new ArrayList<>();
            for (Long value : contentIdList) {
                ContentConcept concept = conceptService.getContentConceptById(value);

                contentResponseList.add(GetConceptResponse.from(concept));
            }
            responseList.add(GetCategoryAndConceptsResponse.from(category, contentResponseList));
        }
        GetCategoryAndConceptsListResponse response = GetCategoryAndConceptsListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/series/{category}")
    @Operation(summary = "시리즈 카테고리별 영상 목록", description = "시리즈 카테고리별 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시리즈 카테고리별 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetConceptListResponse> getSeriesContentByCategory(@PathVariable String category) {
        log.info("시리즈 카테고리별 영상 목록 조회" + "\n" + "getSeriesContentByCategory : " + category);

        Long categoryId = categoryService.getIdByIsMovieAndName(false, category);
        List<Long> conceptIdList = categoryRelationshipService.getConceptIdByCategory(categoryId);
        List<GetConceptResponse> responseList = new ArrayList<>();
        for (Long aLong : conceptIdList) {
            ContentConcept concept = conceptService.getContentConceptById(aLong);

            responseList.add(GetConceptResponse.from(concept));
        }
        GetConceptListResponse response = GetConceptListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/movie")
    @Operation(summary = "영화 메인 영상 목록", description = "영화 메인 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "영화 메인 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetCategoryAndConceptsListResponse> getMovieContent() {
        log.info("영화 메인 영상 목록 조회" + "\n" + "getMovieContent");

        List<Long> movieCategoryIdList = categoryService.getMovieCategoriesId();
        List<GetCategoryAndConceptsResponse> responseList = new ArrayList<>();
        for (Long aLong : movieCategoryIdList) {
            ContentCategory tmp = categoryService.getContentCategoryById(aLong);
            List<Long> contentIdList = categoryRelationshipService.getConceptIdByCategory(aLong);
            List<GetConceptResponse> contentResponseList = new ArrayList<>();
            for (Long value : contentIdList) {
                ContentConcept concept = conceptService.getContentConceptById(value);

                contentResponseList.add(GetConceptResponse.from(concept));
            }
            responseList.add(GetCategoryAndConceptsResponse.from(tmp, contentResponseList));
        }
        GetCategoryAndConceptsListResponse response = GetCategoryAndConceptsListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/movie/{category}")
    @Operation(summary = "영화 카테고리별 영상 목록", description = "영화 카테고리별 영상 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "영화 카테고리별 영상 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetConceptListResponse> getMovieContentByCategory(@PathVariable String category) {
        log.info("영화 카테고리별 영상 목록 조회" + "\n" + "getMovieContentByCategory : " + category);

        Long categoryId = categoryService.getIdByIsMovieAndName(true, category);
        List<Long> conceptIdList = categoryRelationshipService.getConceptIdByCategory(categoryId);
        List<GetConceptResponse> responseList = new ArrayList<>();
        for (Long aLong : conceptIdList) {
            ContentConcept concept = conceptService.getContentConceptById(aLong);

            responseList.add(GetConceptResponse.from(concept));
        }
        GetConceptListResponse response = GetConceptListResponse.from(responseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{keyword}")
    @Operation(summary = "키워드로 검색", description = "키워드로 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "키워드로 콘텐츠 검색 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<List<GetConceptResponse>> getContentConceptByKeyword(@PathVariable String keyword) {
        log.info("키워드로 콘텐츠 검색" + "\n" + "getSeriesCategories : "+keyword);

        List<ContentConcept> conceptList = conceptService.getContentConceptByKeyword(keyword);
        List<GetConceptResponse> responseList = new ArrayList<>();
        for (ContentConcept contentConcept : conceptList) {
            responseList.add(GetConceptResponse.from(contentConcept));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/all")
    @Operation(summary = "콘텐츠 전체 조회", description = "콘텐츠 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "콘텐츠 전체 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    ResponseEntity<GetAllConceptListResponse> getAllContentList() {
        log.info("콘텐츠 전체 조회" + "\n" + "getAllContentList");

        GetAllConceptListResponse response = conceptService.getAllContentList();

        return ResponseEntity.ok(response);
    }
}
