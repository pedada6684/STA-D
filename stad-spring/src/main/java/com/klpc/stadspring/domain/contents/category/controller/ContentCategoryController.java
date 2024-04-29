package com.klpc.stadspring.domain.contents.category.controller;

import com.klpc.stadspring.domain.contents.category.controller.response.GetCategoryListResponse;
import com.klpc.stadspring.domain.contents.category.service.ContentCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "콘텐츠 카테고리 컨트롤러", description = "Contents Category Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents-category")
public class ContentCategoryController {

    private final ContentCategoryService service;

    @GetMapping("/collection/series")
    @Operation(summary = "시리즈 카테고리 리스트 조회", description = "시리즈 카테고리 리스트 조회")
    public ResponseEntity<GetCategoryListResponse> getSeriesCategories() {
        List<String> list = service.getSeriesCategories();
        GetCategoryListResponse response = GetCategoryListResponse.from(list);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/collection/movie")
    @Operation(summary = "영화 카테고리 리스트 조회", description = "영화 카테고리 리스트 조회")
    public ResponseEntity<GetCategoryListResponse> getMovieCategories() {
        List<String> list = service.getMovieCategories();
        GetCategoryListResponse response = GetCategoryListResponse.from(list);

        return ResponseEntity.ok(response);
    }
}
