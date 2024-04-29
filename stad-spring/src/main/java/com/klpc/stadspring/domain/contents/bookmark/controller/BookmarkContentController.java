package com.klpc.stadspring.domain.contents.bookmark.controller;

import com.klpc.stadspring.domain.contents.bookmark.controller.request.AddBookmarkRequest;
import com.klpc.stadspring.domain.contents.bookmark.controller.request.DeleteBookmarkRequest;
import com.klpc.stadspring.domain.contents.bookmark.controller.response.AddBookmarkResponse;
import com.klpc.stadspring.domain.contents.bookmark.controller.response.CheckContentResponse;
import com.klpc.stadspring.domain.contents.bookmark.controller.response.DeleteBookmarkResponse;
import com.klpc.stadspring.domain.contents.bookmark.service.BookmarkedContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping("/check/{detailId}")
    @Operation(summary = "북마크 유무 확인", description = "북마크 유무 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 유무 확인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<CheckContentResponse> checkBookmark(@RequestParam Long userId, @PathVariable Long detailId) {
        boolean tmp = service.checkBookmark(userId, detailId);
        return ResponseEntity.ok(CheckContentResponse.from(tmp));
    }

    @PostMapping("/add")
    @Operation(summary = "북마크 추가", description = "북마크 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 추가 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<AddBookmarkResponse> addBookmark(@RequestBody AddBookmarkRequest request) {
        log.info("AddBookmarkRequest : "+request);

        try {
            AddBookmarkResponse response = service.addBookmark(request.toCommand());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "북마크 삭제", description = "북마크 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<DeleteBookmarkResponse> deleteBookmark(@RequestBody DeleteBookmarkRequest request) {
        log.info("DeleteBookmarkRequest: "+request);

        try {
            DeleteBookmarkResponse response = service.deleteBookmark(request.toCommand());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
