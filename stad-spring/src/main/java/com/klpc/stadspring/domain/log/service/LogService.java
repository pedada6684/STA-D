package com.klpc.stadspring.domain.log.service;

import com.klpc.stadspring.domain.log.controller.response.GetDailyCountResponse;
import com.klpc.stadspring.domain.log.controller.response.GetTotalLogResponse;
import com.klpc.stadspring.domain.log.entity.AdvertClickLog;
import com.klpc.stadspring.domain.log.entity.AdvertVideoLog;
import com.klpc.stadspring.domain.log.entity.OrderLog;
import com.klpc.stadspring.domain.log.repository.*;
import com.klpc.stadspring.domain.log.service.command.*;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class LogService {

    private final AdvertClickLogRepository advertClickLogRepository;
    private final AdvertVideoLogRepository advertVideoLogRepository;
    private final OrderLogRepository orderLogRepository;
    private final AdvertStatisticsRepository advertStatisticsRepository;

    @Transactional
    public AdvertClickLog addAdvertClickLog(AddAdvertClickLogCommand command) {
        log.info("AddAdvertClickLogCommand: " + command);

        AdvertClickLog newAddAdvertClickLog = AdvertClickLog.createNewAdvertClickLog(
                command.getAdvertVideoId(),
                command.getAdvertId(),
                command.getUserId(),
                command.getContentId(),
                command.getRegDate()
        );

        advertClickLogRepository.save(newAddAdvertClickLog);

        return newAddAdvertClickLog;
    }

    @Transactional

    public AdvertVideoLog addAdvertVideoLog(AddAdvertVideoLogCommand command) {
        log.info("AddAdvertVideoLogCommand: " + command);

        AdvertVideoLog newAddAdvertVideoLog = AdvertVideoLog.createNewAdvertVideoLog(
                command.getAdvertVideoId(),
                command.getAdvertId(),
                command.getUserId(),
                command.getContentId(),
                command.getRegDate()
        );

        advertVideoLogRepository.save(newAddAdvertVideoLog);

        return newAddAdvertVideoLog;
    }

    @Transactional

    public OrderLog addOrderLog(AddOrderLogCommand command) {
        log.info("AddOrderLogCommand: " + command);

        OrderLog newOrderLog = OrderLog.createNewOrderLog(
                command.getAdvertId(),
                command.getAdvertVideoId(),
                command.getUserId(),
                command.getOrderId(),
                command.getContentId(),
                command.getProductId(),
                command.getPrice(),
                command.getStatus(),
                command.getRegDate(),
                command.getUpdateDate()
        );

        orderLogRepository.save(newOrderLog);

        return newOrderLog;
    }

    @Transactional

    public OrderLog addOrderCancelLog(AddCancelOrderLogCommand command) {
        log.info("AddCancelOrderLogCommand: " + command);

        OrderLog newOrderLog = OrderLog.createNewOrderLog(
                command.getAdvertId(),
                command.getAdvertVideoId(),
                command.getUserId(),
                command.getOrderId(),
                command.getContentId(),
                command.getProductId(),
                command.getPrice(),
                command.getStatus(),
                command.getRegDate(),
                command.getUpdateDate()
        );

        orderLogRepository.save(newOrderLog);

        return newOrderLog;
    }

    public GetTotalLogResponse getTotalLog(Long advertId) {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        Object[] results = advertStatisticsRepository.getTotalLog(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Object[] result = (Object[]) results[0];

        GetTotalLogResponse response = GetTotalLogResponse.builder().
                totalAdvertClick(result[0] != null ? (Long) result[0] : 0L).
                totalAdvertVideo(result[1] != null ? (Long) result[1] : 0L).
                totalOrder(result[2] != null ? (Long) result[2] : 0L).
//                totalOrderCancel((Long) result[3]).
                totalRevenue(result[3] != null ? (Long) result[3] : 0L).
                build();

        return response;
    }

    public GetDailyCountResponse getDailyAdvertClickCount(Long advertId) {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        List<Object[]> results = advertStatisticsRepository.getDailyAdvertClickCount(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetDailyCountCommand> dailyCounts = results.stream()
                .map(result -> new GetDailyCountCommand((LocalDate) result[0], (Long) result[1]))
                .collect(Collectors.toList());

        return GetDailyCountResponse.builder().list(dailyCounts).build();
    }

    public GetDailyCountResponse getDailyAdvertVideoCount(Long advertId) {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        List<Object[]> results = advertStatisticsRepository.getDailyAdvertVideoCount(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetDailyCountCommand> dailyVideos = results.stream()
                .map(result -> new GetDailyCountCommand((LocalDate) result[0], (Long) result[1]))
                .collect(Collectors.toList());

        return GetDailyCountResponse.builder().list(dailyVideos).build();
    }

    public GetDailyCountResponse getDailyOrderCount(Long advertId) {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        List<Object[]> results = advertStatisticsRepository.getDailyOrderCount(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetDailyCountCommand> dailyOrders = results.stream()
                .map(result -> new GetDailyCountCommand((LocalDate) result[0], (Long) result[1]))
                .collect(Collectors.toList());

        return GetDailyCountResponse.builder().list(dailyOrders).build();
    }

    public GetDailyCountResponse getDailyRevenueCount(Long advertId) {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        List<Object[]> results = advertStatisticsRepository.getDailyOrderCount(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetDailyCountCommand> dailyRevenues = results.stream()
                .map(result -> new GetDailyCountCommand((LocalDate) result[0], (Long) result[1]))
                .collect(Collectors.toList());

        return GetDailyCountResponse.builder().list(dailyRevenues).build();
    }
}

