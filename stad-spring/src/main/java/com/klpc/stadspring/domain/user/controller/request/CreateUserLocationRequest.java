package com.klpc.stadspring.domain.user.controller.request;

import com.klpc.stadspring.domain.user.service.command.CreateUserLocationCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserLocationRequest {
    private Long userId;
    private String location;
    private String name;
    private String phone;
    private String locationNick;

    public CreateUserLocationCommand toCommand(){
        return CreateUserLocationCommand.builder()
                .userId(userId)
                .location(location)
                .name(name)
                .phone(phone)
                .locationNick(locationNick)
                .build();
    }
}
