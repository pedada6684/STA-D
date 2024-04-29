package com.klpc.stadspring.domain.contents.watched.controller;

import com.klpc.stadspring.domain.contents.watched.controller.request.AddWatchingContentRequest;
import com.klpc.stadspring.domain.contents.watched.controller.request.ModifyWatchingContentRequest;
import com.klpc.stadspring.domain.contents.watched.controller.response.AddWatchingContentResponse;
import com.klpc.stadspring.domain.contents.watched.controller.response.ModifyWatchingContentResponse;
import com.klpc.stadspring.domain.contents.watched.service.WatchedContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "시청 콘텐츠 컨트롤러", description = "Watched Contents Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents-watch")
public class WatchedContentController {
    private final WatchedContentService service;

    @PostMapping("/add")
    @Operation(summary = "시청 중인 영상 등록", description = "Watched Content Add API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시청 중인 영상 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<AddWatchingContentResponse> addWatchingContent(@RequestBody AddWatchingContentRequest request) {
        log.info("시청 중인 영상 등록" + "\n" + "AddWatchingContentRequest : "+request);

        try {
            AddWatchingContentResponse response = service.addWatchingContent(request.toCommand());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update")
    @Operation(summary = "시청 중인 영상 수정", description = "Watched Content Modify API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시청 중인 영상 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<ModifyWatchingContentResponse> modifyWatchingContent(@RequestBody ModifyWatchingContentRequest request) {
        log.info("시청 중인 영상 수정" + "\n" + "ModifyWatchingContentRequest : "+request);

        try {
            ModifyWatchingContentResponse response = service.modifyWatchingContent(request.toCommand());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
