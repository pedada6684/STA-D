package com.klpc.stadspring.domain.log.controller.response;

import com.klpc.stadspring.domain.log.service.command.GetDailyCountCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetDailyCountResponse {
    List<GetDailyCountCommand> list;
}
