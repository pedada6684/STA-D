package com.klpc.stadspring.domain.contents.detail.controller;

import com.klpc.stadspring.domain.contents.detail.service.ContentDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
@Tag(name = "contents 컨트롤러", description = "콘텐츠 API 입니다.")
public class ContentDetailController {
    private final ContentDetailService service;

    @GetMapping("/streaming/{contentId}")
    ResponseEntity<ResourceRegion> streamingPublicVideo(@RequestHeader HttpHeaders httpHeaders, @PathVariable Long contentId){
        String videoUrl = service.getVideoUrlById(contentId);
        return service.streamingPublicVideo(httpHeaders, videoUrl);
    }

}
