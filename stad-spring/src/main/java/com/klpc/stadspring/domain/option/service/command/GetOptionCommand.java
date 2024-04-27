package com.klpc.stadspring.domain.option.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetOptionCommand {
    String name;
    String value;
}
