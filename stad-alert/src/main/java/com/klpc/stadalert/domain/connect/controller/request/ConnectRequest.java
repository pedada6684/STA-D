package com.klpc.stadalert.domain.connect.controller.request;

import com.klpc.stadalert.global.service.command.ConnectCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConnectRequest {
    String type;
    String userId;

    public ConnectRequest(String type, String userId) {
        this.type = type;
        this.userId = userId;
    }

    public ConnectCommand toCommand(){
        return ConnectCommand.builder()
                .userId(userId)
                .type(type)
                .build();
    }
}
