package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateUserLocationCommand {
    private Long userId;
    private String location;
    private String name;
    private String phone;
    private String locationNick;
}