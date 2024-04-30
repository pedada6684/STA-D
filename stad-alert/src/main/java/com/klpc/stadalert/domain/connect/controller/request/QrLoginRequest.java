package com.klpc.stadalert.domain.connect.controller.request;

import com.klpc.stadalert.domain.connect.service.command.QrLoginCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QrLoginRequest {
    String tvId;
    Long userId;
    public QrLoginCommand toCommand(){
        return QrLoginCommand.builder()
                .userId(userId)
                .tvId(tvId)
                .build();
    }
}
