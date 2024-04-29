package com.klpc.stadspring.domain.log.service.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetDailyCountCommand {
    LocalDate date;
    Long value;
}
