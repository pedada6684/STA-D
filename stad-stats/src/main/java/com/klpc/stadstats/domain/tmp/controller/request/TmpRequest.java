package com.klpc.stadstats.domain.tmp.controller.request;

import com.klpc.stadstats.domain.tmp.service.command.TmpCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TmpRequest {
    String type;
    Long userId;
    public TmpCommand toCommand(){
        return TmpCommand.builder()
                .userId(userId)
                .type(type)
                .build();
    }
}
