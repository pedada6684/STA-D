package com.klpc.stadspring.domain.user.controller.response;

import com.klpc.stadspring.domain.user.entity.UserLocation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserLocationResponse {
    private Long userId;
    private Long locationId;
    private String location;
    private String name;
    private String phone;
    private String locationNick;


    public static CreateUserLocationResponse from(UserLocation userLocation){
        return CreateUserLocationResponse.builder()
                .userId(userLocation.getUser().getId())
                .locationId(userLocation.getId())
                .location(userLocation.getLocation())
                .name(userLocation.getName())
                .phone(userLocation.getPhone())
                .locationNick(userLocation.getLocationNick())
                .build();
    }
}
