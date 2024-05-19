package com.klpc.stadstats.domain.log.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class GetDailyCountCommand {
    LocalDate date;
    Long value;
}
