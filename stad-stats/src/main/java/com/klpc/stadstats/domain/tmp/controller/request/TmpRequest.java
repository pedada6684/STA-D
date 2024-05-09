package com.klpc.stadstats.domain.tmp.controller.request;

import com.klpc.stadstats.domain.tmp.service.command.TmpCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TmpRequest {
    String type;
    Long id;
    public TmpCommand toCommand(){
        return TmpCommand.builder()
                .id(id)
                .type(type)
                .build();
    }
}
