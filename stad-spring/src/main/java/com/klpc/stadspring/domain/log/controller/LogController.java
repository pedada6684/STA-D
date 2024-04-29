package com.klpc.stadspring.domain.log.controller;

import com.klpc.stadspring.domain.log.controller.request.AddAdvertClickLogRequest;
import com.klpc.stadspring.domain.log.controller.request.AddAdvertVideoLogRequest;
import com.klpc.stadspring.domain.log.controller.request.AddOrderLogRequest;
import com.klpc.stadspring.domain.log.controller.request.AddOrderReturnLogRequest;
import com.klpc.stadspring.domain.log.entity.AdvertClickLog;
import com.klpc.stadspring.domain.log.entity.AdvertVideoLog;
import com.klpc.stadspring.domain.log.entity.OrderLog;
import com.klpc.stadspring.domain.log.entity.OrderReturnLog;
import com.klpc.stadspring.domain.log.service.LogService;
import com.klpc.stadspring.domain.product_review.controller.request.ProductReviewPostRequest;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "통계 컨트롤러", description = "통계 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {
    private final LogService logService;

    @PostMapping(value = "/click")
    @Operation(summary = "광고 클릭 로그 추가", description = "광고 클릭 로그 추가")
    public ResponseEntity<?> AddAdvertClickLog(@RequestBody AddAdvertClickLogRequest request) {
        log.info("AddAdvertClickLogRequest: " + request);
        try {
            AdvertClickLog advertClickLog = logService.addAdvertClickLog(request.toCommand());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/advert-log")
    @Operation(summary = "광고 시청 로그 추가", description = "광고 시청 로그 추가")
    public ResponseEntity<?> AddAdvertVideoLog(@RequestBody AddAdvertVideoLogRequest request) {
        log.info("AddAdvertClickLogRequest: " + request);
        try {
            AdvertVideoLog advertVideoLog = logService.addAdvertVideoLog(request.toCommand());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/Order-log")
    @Operation(summary = "주문 현황 로그 추가", description = "주문 현황 로그 추가")
    public ResponseEntity<?> AddOrderLog(@RequestBody AddOrderLogRequest request) {
        log.info("AddOrderLogRequest: " + request);
        try {
            OrderLog orderLog = logService.addOrderLog(request.toCommand());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/return-log")
    @Operation(summary = "반품 로그 추가", description = "반품 로그 추가")
    public ResponseEntity<?> AddOrderReturnLog(@RequestBody AddOrderReturnLogRequest request) {
        log.info("AddOrderReturnLogRequest: " + request);
        try {
            OrderReturnLog orderReturnLog = logService.addOrderReturnLog(request.toCommand());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
