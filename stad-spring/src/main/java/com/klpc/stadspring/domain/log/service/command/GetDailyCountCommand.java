package com.klpc.stadspring.domain.log.service.command;

import com.klpc.stadspring.domain.log.entity.AdvertVideoLog;
import com.klpc.stadspring.domain.log.entity.OrderLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class GetDailyCountCommand {
    LocalDate date;
    Long value;
}
