package com.klpc.stadalert.domain.connect.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QrLoginCommand {
    Long userId;
    String tvId;
}
