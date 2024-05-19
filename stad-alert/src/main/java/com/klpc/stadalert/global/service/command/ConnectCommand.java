package com.klpc.stadalert.global.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectCommand {
    String userId;
    String type; // tv, app
}
