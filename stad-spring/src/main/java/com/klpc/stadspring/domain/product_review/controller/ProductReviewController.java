package com.klpc.stadspring.domain.product_review.controller;

import com.klpc.stadspring.domain.product_review.controller.request.ProductReviewPostRequest;
import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewListResponse;
import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.product_review.service.ProductReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "상품 리뷰 컨트롤러", description = "Product Review Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ProductReviewController {

    private final ProductReviewService productReviewService;


    @GetMapping("")
    @Operation(summary = "리뷰 상세 조회", description = "리뷰 상세 조회")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GetProductReviewResponse.class)))
    public ResponseEntity<GetProductReviewResponse> getReviewInfo(@RequestParam("id") Long id) {
        ProductReview productReview = productReviewService.getProductReviewInfo(id);
        GetProductReviewResponse response = GetProductReviewResponse.from(productReview);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/regist", consumes = "multipart/form-data", produces = "application/json")
    @Operation(summary = "리뷰 등록", description = "리뷰 등록")
    public ResponseEntity<?> registReview(@ModelAttribute ProductReviewPostRequest request) {
        log.info("ProductReviewPostRequest: " + request);
        try {
            ProductReview review = productReviewService.registReview(request.toCommand());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<?> deleteReview(@RequestParam Long id) {
        productReviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    // 리뷰 리스트 조회
    @GetMapping("/list")
    @Operation(summary = "특정 상품에 속한 리뷰 리스트 조회", description = "특정 상품에 속한 리뷰 리스트 조회")
    public ResponseEntity<?> getProductReviewListByAdverseId(@RequestParam Long productId) {
        GetProductReviewListResponse response = productReviewService.getReviewListByProductId(productId);

        // 변환된 응답을 ResponseEntity에 담아 반환
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/user")
    @Operation(summary = "유저가 작성한 리뷰 목록", description = "유저가 작성한 리뷰 목록")
    public ResponseEntity<?> getProductReviewListByUserId(@RequestParam Long userId) {
        GetProductReviewListResponse response = productReviewService.getProductReviewListByUserId(userId);

        // 변환된 응답을 ResponseEntity에 담아 반환
        return ResponseEntity.ok(response);
    }
}
