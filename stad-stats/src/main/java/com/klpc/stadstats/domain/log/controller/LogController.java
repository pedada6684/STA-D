package com.klpc.stadstats.domain.log.controller;

import com.klpc.stadstats.domain.log.controller.request.AddAdvertClickLogRequest;
import com.klpc.stadstats.domain.log.controller.request.AddAdvertVideoLogRequest;
import com.klpc.stadstats.domain.log.controller.request.AddCancelOrderLogRequest;
import com.klpc.stadstats.domain.log.controller.request.AddOrderLogRequest;
import com.klpc.stadstats.domain.log.controller.response.GetDailyCountResponse;
import com.klpc.stadstats.domain.log.controller.response.GetTotalLogResponse;
import com.klpc.stadstats.domain.log.entity.AdvertClickLog;
import com.klpc.stadstats.domain.log.entity.AdvertVideoLog;
import com.klpc.stadstats.domain.log.entity.OrderLog;
import com.klpc.stadstats.domain.log.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "통계 컨트롤러", description = "통계 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {
    private final LogService logService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping(value = "/click")
    @Operation(summary = "광고 클릭 로그 추가", description = "광고 클릭 로그 추가")
    public ResponseEntity<?> AddAdvertClickLog(@RequestBody AddAdvertClickLogRequest request) {
        log.info("AddAdvertClickLogRequest: " + request);
        try {
            AdvertClickLog advertClickLog = logService.addAdvertClickLog(request.toCommand());
            kafkaTemplate.send("log-add-click", advertClickLog);
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
            kafkaTemplate.send("log-add-video", advertVideoLog);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/order-log")
    @Operation(summary = "주문 현황 로그 추가", description = "주문 현황 로그 추가")
    public ResponseEntity<?> AddOrderLog(@RequestBody AddOrderLogRequest request) {
        log.info("AddOrderLogRequest: " + request);
        try {
            OrderLog orderLog = logService.addOrderLog(request.toCommand());
            kafkaTemplate.send("log-add-order", orderLog);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/cancel-log")
    @Operation(summary = "주문 취소", description = "주문 취소")
    public ResponseEntity<?> AddOrderReturnLog(@RequestBody AddCancelOrderLogRequest request) {
        log.info("AddOrderReturnLogRequest: " + request);
        try {
            OrderLog orderCancelLog = logService.addOrderCancelLog(request.toCommand());
            kafkaTemplate.send("log-add-cancel", orderCancelLog);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/total")
    @Operation(summary = "30일 데이터 총합", description = "30일짜리 데이터")
    public ResponseEntity<?> GetTotalLog(@RequestParam("advertId") Long advertId)  {
        try {
            GetTotalLogResponse response = logService.getTotalLog(advertId);
            kafkaTemplate.send("log-total", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/daily/click")
    @Operation(summary = "30일 동안 각 날짜 광고 클릭 수", description = "30일 동안 각 날짜 광고 클릭 수")
    public ResponseEntity<?> GetDailyAdvertClick(@RequestParam("advertId") Long advertId)  {
        try {
            GetDailyCountResponse response = logService.getDailyAdvertClickCount(advertId);
            log.info("success");
            kafkaTemplate.send("log-click", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info("problem?");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/daily/advert-video")
    @Operation(summary = "30일 동안 각 날짜 광고 시청 수", description = "30일 동안 각 날짜 광고 시청 수")
    public ResponseEntity<?> GetDailyAdvertVideo(@RequestParam("advertId") Long advertId)  {
        try {
            GetDailyCountResponse response = logService.getDailyAdvertVideoCount(advertId);
            kafkaTemplate.send("log-video", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/daily/order")
    @Operation(summary = "30일 동안 각 날짜 주문 수", description = "30일 동안 각 날짜 광고 주문 수")
    public ResponseEntity<?> GetDailyOrder(@RequestParam("advertId") Long advertId)  {
        try {
            GetDailyCountResponse response = logService.getDailyOrderCount(advertId);
            kafkaTemplate.send("log-order", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/daily/revenue")
    @Operation(summary = "30일 동안 각 날짜 수익", description = "30일 동안 각 날짜 수익")
    public ResponseEntity<?> GetDailyRevenue(@RequestParam("advertId") Long advertId)  {
        try {
            GetDailyCountResponse response = logService.getDailyRevenueCount(advertId);
            kafkaTemplate.send("log-revenue", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
