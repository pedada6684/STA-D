package com.klpc.stadspring.domain.contents.bookmark.controller;

import com.klpc.stadspring.domain.contents.bookmark.controller.response.CheckContentResponse;
import com.klpc.stadspring.domain.contents.bookmark.service.BookmarkedContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents-bookmark")
@Tag(name = "북마크 콘텐츠 컨트롤러", description = "Bookmark Content Controller API")
public class BookmarkContentController {
    private final BookmarkedContentService service;

    @GetMapping("/check/{contentId}")
    @Operation(summary = "북마크 유무 확인", description = "북마크 유무 확인")
    public ResponseEntity<CheckContentResponse> checkBookmark(@RequestParam Long userId, @PathVariable Long contentId) {
        boolean tmp = service.checkBookmark(userId, contentId);
        return ResponseEntity.ok(CheckContentResponse.from(tmp));
    }
}
