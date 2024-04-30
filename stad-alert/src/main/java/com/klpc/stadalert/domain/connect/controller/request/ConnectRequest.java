package com.klpc.stadalert.domain.connect.controller.request;

import com.klpc.stadalert.global.service.command.ConnectCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConnectRequest {
    String type;
    String tmpId;
    Long userId;
    public ConnectCommand toCommand(){
        String strUserId;
        if (tmpId != null && !tmpId.isEmpty()){
            strUserId = tmpId;
        }else{
            strUserId = Long.toString(userId);
        }
        return ConnectCommand.builder()
                .userId(strUserId)
                .type(type)
                .build();
    }
}
