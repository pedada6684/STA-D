package com.klpc.stadspring.domain.classification.controller;

import com.klpc.stadspring.domain.classification.dto.ClassificationRequest;
import com.klpc.stadspring.domain.classification.dto.ClassificationResponse;
import com.klpc.stadspring.domain.classification.dto.UserCategoryRequest;
import com.klpc.stadspring.domain.classification.dto.UserCategoryResponse;
import com.klpc.stadspring.domain.classification.service.ClassificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "카테고리 분류 컨트롤러", description = "Classification Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/classification")
public class ClassificationController {

    private final ClassificationService classificationService;

    @PostMapping("/video")
    public ResponseEntity<List<ClassificationResponse>> classifyVideos(@RequestBody List<ClassificationRequest> ClassificationRequestList) {
        List<ClassificationResponse> ClassificationResponseList = classificationService.getCategory(ClassificationRequestList);
        return ResponseEntity.ok(ClassificationResponseList);
    }

    @PostMapping("/user")
    public ResponseEntity<List<UserCategoryResponse>> classifyUserCategory(@RequestBody UserCategoryRequest userCategoryRequest) {
        List<UserCategoryResponse> userCategoryResponse = classificationService.getUserCategory(userCategoryRequest);
        return ResponseEntity.ok(userCategoryResponse);
    }
}
