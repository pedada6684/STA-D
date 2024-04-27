package com.klpc.stadspring.domain.option.service.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetOptionListCommand {
    List<GetOptionCommand> list;
}
