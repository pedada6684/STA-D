package com.klpc.stadspring.domain.log.service;

import com.klpc.stadspring.domain.log.controller.response.GetDailyCountResponse;
import com.klpc.stadspring.domain.log.controller.response.GetTotalLogResponse;
import com.klpc.stadspring.domain.log.entity.AdvertClickLog;
import com.klpc.stadspring.domain.log.entity.AdvertVideoLog;
import com.klpc.stadspring.domain.log.entity.OrderLog;
import com.klpc.stadspring.domain.log.repository.*;
import com.klpc.stadspring.domain.log.service.command.*;
import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewListResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.product_review.service.command.ProductReviewInfoCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class LogService {

    private final AdvertClickLogRepository advertClickLogRepository;
    private final AdvertVideoLogRepository advertVideoLogRepository;
    private final OrderLogRepository orderLogRepository;
    private final OrderReturnLogRepository orderReturnLogRepository;
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
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        Object[] results = advertStatisticsRepository.getTotalLog(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Object[] result = (Object[]) results[0];

        GetTotalLogResponse response = GetTotalLogResponse.builder().
                totalAdvertClick((Long) result[0]).
                totalAdvertVideo((Long) result[1]).
                totalOrder((Long) result[2]).
                totalOrderCancel((Long) result[3]).
                totalRevenue((Long) result[4]).
                build();

        return response;
    }

    public GetDailyCountResponse getDailyAdvertCilckCount() {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        List<GetDailyCountCommand> dailyAdvertClickCountList = advertStatisticsRepository.getDailyAdvertClickCount(thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        return GetDailyCountResponse.builder().list(dailyAdvertClickCountList).build();
    }

    public GetDailyCountResponse getDailyAdvertVideoCount() {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        List<GetDailyCountCommand> dailyAdvertVideoCountList = advertStatisticsRepository.getDailyAdvertVideoCount(thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        return GetDailyCountResponse.builder().list(dailyAdvertVideoCountList).build();
    }

    public GetDailyCountResponse getDailyOrderCount() {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        List<GetDailyCountCommand> dailyOrderCountList = advertStatisticsRepository.getDailyOrderCount(thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        return GetDailyCountResponse.builder().list(dailyOrderCountList).build();
    }

    public GetDailyCountResponse getDailyRevenueCount() {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        List<GetDailyCountCommand> dailyRevenueCountList = advertStatisticsRepository.getDailyRevenue(thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        return GetDailyCountResponse.builder().list(dailyRevenueCountList).build();
    }
}

