package com.klpc.stadstats.domain.log.service;

import com.klpc.stadstats.domain.log.controller.response.GetAdvertIdListResponse;
import com.klpc.stadstats.domain.log.controller.response.GetDailyCountResponse;
import com.klpc.stadstats.domain.log.controller.response.GetTotalLogResponse;
import com.klpc.stadstats.domain.log.entity.AdvertClickLog;
import com.klpc.stadstats.domain.log.entity.AdvertVideoLog;
import com.klpc.stadstats.domain.log.entity.OrderLog;
import com.klpc.stadstats.domain.log.repository.AdvertClickLogRepository;
import com.klpc.stadstats.domain.log.repository.AdvertStatisticsRepository;
import com.klpc.stadstats.domain.log.repository.AdvertVideoLogRepository;
import com.klpc.stadstats.domain.log.repository.OrderLogRepository;
import com.klpc.stadstats.domain.log.service.command.*;
import com.klpc.stadstats.global.response.ErrorCode;
import com.klpc.stadstats.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LogService {

    private final AdvertClickLogRepository advertClickLogRepository;
    private final AdvertVideoLogRepository advertVideoLogRepository;
    private final OrderLogRepository orderLogRepository;
    private final AdvertStatisticsRepository advertStatisticsRepository;
    private final RestTemplate restTemplate;

    @Value("${spring.stad-main}")
    private String stadMainUrl;

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
                totalOrderCancel(result[2] != null ? (Long) result[3] : 0L).
                totalRevenue(result[3] != null ? (Long) result[4] : 0L).
                build();

        return response;
    }

    public GetDailyCountResponse getDailyAdvertClickCount(Long advertId) {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        LocalDate today = LocalDate.now();

        List<Object[]> results = advertStatisticsRepository.getDailyAdvertClickCount(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Map<LocalDate, Long> resultMapped = results.stream()
                .collect(Collectors.toMap(
                        result -> (LocalDate) result[0],
                        result -> (Long) result[1]
                ));

        List<GetDailyCountCommand> dailyCounts = LongStream.rangeClosed(0, ChronoUnit.DAYS.between(thirtyDaysAgo, today))
                .mapToObj(i -> thirtyDaysAgo.plusDays(i))
                .map(date -> new GetDailyCountCommand(date, resultMapped.getOrDefault(date, 0L)))
                .collect(Collectors.toList());

        return GetDailyCountResponse.builder().list(dailyCounts).build();
    }

    public GetDailyCountResponse getDailyAdvertVideoCount(Long advertId) {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        LocalDate today = LocalDate.now();

        List<Object[]> results = advertStatisticsRepository.getDailyAdvertVideoCount(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Map<LocalDate, Long> resultMapped = results.stream()
                .collect(Collectors.toMap(
                        result -> (LocalDate) result[0],
                        result -> (Long) result[1]
                ));

        List<GetDailyCountCommand> dailyAdvertVideoCounts = LongStream.rangeClosed(0, ChronoUnit.DAYS.between(thirtyDaysAgo, today))
                .mapToObj(i -> thirtyDaysAgo.plusDays(i))
                .map(date -> new GetDailyCountCommand(date, resultMapped.getOrDefault(date, 0L)))
                .collect(Collectors.toList());

        return GetDailyCountResponse.builder().list(dailyAdvertVideoCounts).build();
    }

    public GetDailyCountResponse getDailyOrderCount(Long advertId) {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        LocalDate today = LocalDate.now();

        List<Object[]> results = advertStatisticsRepository.getDailyOrderCount(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Map<LocalDate, Long> resultMapped = results.stream()
                .collect(Collectors.toMap(
                        result -> (LocalDate) result[0],
                        result -> (Long) result[1]
                ));

        List<GetDailyCountCommand> dailyOrders = LongStream.rangeClosed(0, ChronoUnit.DAYS.between(thirtyDaysAgo, today))
                .mapToObj(i -> thirtyDaysAgo.plusDays(i))
                .map(date -> new GetDailyCountCommand(date, resultMapped.getOrDefault(date, 0L)))
                .collect(Collectors.toList());

        return GetDailyCountResponse.builder().list(dailyOrders).build();
    }


    public GetDailyCountResponse getDailyRevenueCount(Long advertId) {

        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        LocalDate today = LocalDate.now();

        List<Object[]> results = advertStatisticsRepository.getDailyRevenue(advertId, thirtyDaysAgo)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Map<LocalDate, Long> resultMapped = results.stream()
                .collect(Collectors.toMap(
                        result -> (LocalDate) result[0],
                        result -> (Long) result[1]
                ));

        List<GetDailyCountCommand> dailyRevenues = LongStream.rangeClosed(0, ChronoUnit.DAYS.between(thirtyDaysAgo, today))
                .mapToObj(i -> thirtyDaysAgo.plusDays(i))
                .map(date -> new GetDailyCountCommand(date, resultMapped.getOrDefault(date, 0L)))
                .collect(Collectors.toList());

        return GetDailyCountResponse.builder().list(dailyRevenues).build();
    }

    public GetAdvertIdListResponse listenAdvertIdList() {
        GetAdvertIdListResponse response = restTemplate.getForObject(stadMainUrl+"/advert/get-advert-id-list", GetAdvertIdListResponse.class);
        return response;
    }

    public GetAdvertIdListResponse getAdvertIdListByUser(Long userId) {
        List<Long> advertIdList = advertVideoLogRepository.getAdvertVideoIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Collections.reverse(advertIdList);

        GetAdvertIdListResponse response = GetAdvertIdListResponse.builder()
                .advertIdList(advertIdList)
                .build();

        return response;
    }
}

