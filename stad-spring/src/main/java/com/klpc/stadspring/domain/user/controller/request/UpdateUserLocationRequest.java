package com.klpc.stadspring.domain.user.controller.request;

import com.klpc.stadspring.domain.user.service.command.CreateUserLocationCommand;
import com.klpc.stadspring.domain.user.service.command.UpdateUserLocationCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserLocationRequest {
    private Long userId;
    private Long locationId;
    private String location;
    private String name;
    private String phone;
    private String locationNick;

    public UpdateUserLocationCommand toCommand(){
        return UpdateUserLocationCommand.builder()
                .userId(userId)
                .locationId(locationId)
                .location(location)
                .name(name)
                .phone(phone)
                .locationNick(locationNick)
                .build();
    }
}
