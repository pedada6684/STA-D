package com.klpc.stadstats.domain.log.controller;

import com.klpc.stadstats.domain.log.controller.event.AddAdvertVideoLogEvent;
import com.klpc.stadstats.domain.log.controller.event.AddOrderCancelLogEvent;
import com.klpc.stadstats.domain.log.controller.event.AddOrderLogEvent;
import com.klpc.stadstats.domain.log.controller.request.AddAdvertClickLogRequest;
import com.klpc.stadstats.domain.log.controller.response.GetAdvertIdListResponse;
import com.klpc.stadstats.domain.log.controller.response.GetDailyCountResponse;
import com.klpc.stadstats.domain.log.controller.response.GetTotalLogResponse;
import com.klpc.stadstats.domain.log.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
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
        logService.addAdvertClickLog(request.toCommand());
        return ResponseEntity.ok().build();
    }
    @KafkaListener(topics = "advert-watch-log", groupId = "log-group", containerFactory = "addAdvertVideoLogEventConcurrentKafkaListenerContainerFactory")
    public void onAddAdvertWatchLogEvent(AddAdvertVideoLogEvent event) {
        log.info("AddAdvertVideoLogEvent: " + event);
        logService.addAdvertVideoLog(event.toCommand());
    }

    @KafkaListener(topics = "order-log", groupId = "log-group", containerFactory = "addOrderLogEventConcurrentKafkaListenerContainerFactory")
    public void onAddOrderLogEvent(AddOrderLogEvent event) {
        log.info("AddOrderLogEvent: " + event);
        logService.addOrderLog(event.toCommand());
    }

    @KafkaListener(topics = "order-cancel-log", groupId = "log-group", containerFactory = "addOrderCancelLogEventConcurrentKafkaListenerContainerFactory")
    public void onAddOrderCancelLogEvent(AddOrderCancelLogEvent event) {
        log.info("AddOrderCancelLogEvent: " + event);
        logService.addOrderCancelLog(event.toCommand());
    }

    @GetMapping("/total")
    @Operation(summary = "30일 데이터 총합", description = "30일짜리 데이터")
    public ResponseEntity<?> GetTotalLog(@RequestParam("advertId") Long advertId)  {
        GetTotalLogResponse response = logService.getTotalLog(advertId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily/click")
    @Operation(summary = "30일 동안 각 날짜 광고 클릭 수", description = "30일 동안 각 날짜 광고 클릭 수")
    public ResponseEntity<?> GetDailyAdvertClick(@RequestParam("advertId") Long advertId)  {
        GetDailyCountResponse response = logService.getDailyAdvertClickCount(advertId);
        log.info("success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily/advert-video")
    @Operation(summary = "30일 동안 각 날짜 광고 시청 수", description = "30일 동안 각 날짜 광고 시청 수")
    public ResponseEntity<?> GetDailyAdvertVideo(@RequestParam("advertId") Long advertId)  {
        GetDailyCountResponse response = logService.getDailyAdvertVideoCount(advertId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily/order")
    @Operation(summary = "30일 동안 각 날짜 주문 수", description = "30일 동안 각 날짜 광고 주문 수")
    public ResponseEntity<?> GetDailyOrder(@RequestParam("advertId") Long advertId)  {
        GetDailyCountResponse response = logService.getDailyOrderCount(advertId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily/revenue")
    @Operation(summary = "30일 동안 각 날짜 수익", description = "30일 동안 각 날짜 수익")
    public ResponseEntity<?> GetDailyRevenue(@RequestParam("advertId") Long advertId)  {
        GetDailyCountResponse response = logService.getDailyRevenueCount(advertId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/advert-id/list")
    @Operation(summary = "유저가 본 광고 아이디 조회", description = "유저가 본 광고 아이디 조회")
    public ResponseEntity<?> getAdvertIdByUserId(@RequestParam("userId") Long userId)  {
        GetAdvertIdListResponse response = logService.getAdvertIdListByUser(userId);
        return ResponseEntity.ok(response);
    }
}
