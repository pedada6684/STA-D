package com.klpc.stadspring.domain.user.controller.response;

import com.klpc.stadspring.domain.user.entity.UserLocation;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GetUserLocationResponse {
    List<UserLocationDto> userLocationList;

    public static GetUserLocationResponse from(List<UserLocation> userLocations){
        ArrayList<UserLocationDto> dtoList = new ArrayList<>();
        for (UserLocation userLocation : userLocations) {
            dtoList.add(UserLocationDto.from(userLocation));
        }
        return GetUserLocationResponse.builder()
                .userLocationList(dtoList)
                .build();
    }

    @Data
    @Builder
    public static class UserLocationDto {
        private Long id;
        private String location;
        private String name;
        private String phone;
        private String locationNick;

        public static UserLocationDto from(UserLocation userLocation){
            return UserLocationDto.builder()
                    .id(userLocation.getId())
                    .location(userLocation.getLocation())
                    .name(userLocation.getName())
                    .phone(userLocation.getPhone())
                    .locationNick(userLocation.getLocationNick())
                    .build();
        }
    }
}


