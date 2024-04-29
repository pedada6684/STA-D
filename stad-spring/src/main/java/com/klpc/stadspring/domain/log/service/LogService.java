package com.klpc.stadspring.domain.log.service;

import com.klpc.stadspring.domain.log.controller.response.GetTotalLogResponse;
import com.klpc.stadspring.domain.log.entity.AdvertClickLog;
import com.klpc.stadspring.domain.log.entity.AdvertVideoLog;
import com.klpc.stadspring.domain.log.entity.OrderLog;
import com.klpc.stadspring.domain.log.entity.OrderReturnLog;
import com.klpc.stadspring.domain.log.repository.*;
import com.klpc.stadspring.domain.log.service.command.*;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class LogService {

    private final AdvertClickLogRepository advertClickLogRepository;
    private final AdvertVideoLogRepository advertVideoLogRepository;
    private final OrderLogRepository orderLogRepository;
    private final OrderReturnLogRepository orderReturnLogRepository;
    private final TotalLogRepository totalLogRepository;

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

    public OrderReturnLog addOrderReturnLog(AddOrderReturnLogCommand command) {
        log.info("AddOrderReturnLogCommand: " + command);

        OrderReturnLog newOrderReturnLog = OrderReturnLog.createNewOrderReturnLog(
                command.getOrderId(),
                command.getUserId(),
                command.getAdvertId(),
                command.getRegDate()
        );

        orderReturnLogRepository.save(newOrderReturnLog);

        return newOrderReturnLog;
    }

    public GetTotalLogResponse getTotalLog(Long advertId) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        Object[] results = totalLogRepository.getTotalLog(advertId, thirtyDaysAgo)
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
}

