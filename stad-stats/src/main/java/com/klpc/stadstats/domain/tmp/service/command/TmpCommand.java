package com.klpc.stadstats.domain.tmp.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TmpCommand {
    Long userId;
    String type;
}
