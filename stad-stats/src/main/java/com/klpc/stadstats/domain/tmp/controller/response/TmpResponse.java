package com.klpc.stadstats.domain.tmp.controller.response;

import com.klpc.stadstats.domain.tmp.entity.Tmp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TmpResponse {
    String type;
    public static TmpResponse from(Tmp tmp){
        return TmpResponse.builder()
                .type(tmp.getType())
                .build();
    }

}
