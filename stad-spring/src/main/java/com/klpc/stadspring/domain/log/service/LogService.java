package com.klpc.stadspring.domain.log.service;

import com.klpc.stadspring.domain.log.entity.AdvertClickLog;
import com.klpc.stadspring.domain.log.entity.AdvertVideoLog;
import com.klpc.stadspring.domain.log.entity.OrderLog;
import com.klpc.stadspring.domain.log.entity.OrderReturnLog;
import com.klpc.stadspring.domain.log.repository.AdvertClickLogRepository;
import com.klpc.stadspring.domain.log.repository.AdvertVideoLogRepository;
import com.klpc.stadspring.domain.log.repository.OrderLogRepository;
import com.klpc.stadspring.domain.log.repository.OrderReturnLogRepository;
import com.klpc.stadspring.domain.log.service.command.AddAdvertClickLogCommand;
import com.klpc.stadspring.domain.log.service.command.AddAdvertVideoLogCommand;
import com.klpc.stadspring.domain.log.service.command.AddOrderLogCommand;
import com.klpc.stadspring.domain.log.service.command.AddOrderReturnLogCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Service
public class LogService {

    private final AdvertClickLogRepository advertClickLogRepository;
    private final AdvertVideoLogRepository advertVideoLogRepository;
    private final OrderLogRepository orderLogRepository;
    private final OrderReturnLogRepository orderReturnLogRepository;

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

    public OrderLog addOrderLog(AddOrderLogCommand command) {
        log.info("AddOrderLogCommand: " + command);

        OrderLog newOrderLog = OrderLog.createNewOrderLog(
                command.getAdvertVideoId(),
                command.getUserId(),
                command.getContentId(),
                command.getProductId(),
                command.getStatus(),
                command.getRegDate(),
                command.getUpdateDate()
        );

        orderLogRepository.save(newOrderLog);

        return newOrderLog;
    }

    public OrderReturnLog addOrderReturnLog(AddOrderReturnLogCommand command) {
        log.info("AddOrderReturnLogCommand: " + command);

        OrderReturnLog newOrderReturnLog = OrderReturnLog.createNewOrderReturnLog(
                command.getOrderId(),
                command.getUserId(),
                command.getRegDate()
        );

        orderReturnLogRepository.save(newOrderReturnLog);

        return newOrderReturnLog;
    }
}

