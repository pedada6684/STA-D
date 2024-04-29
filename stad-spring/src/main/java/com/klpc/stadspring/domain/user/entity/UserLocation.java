package com.klpc.stadspring.domain.user.entity;

import com.klpc.stadspring.domain.user.service.command.UpdateUserLocationCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLocation {

    @Id
    @GeneratedValue
    @Column(name = "user_location_id")
    private Long id;
    private String location;
    private String name;
    private String phone;
    private String locationNick;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static UserLocation createNewUserLocation(
            User user,
            String location,
            String name,
            String phone,
            String locationNick
    ){
        UserLocation userLocation = new UserLocation();
        userLocation.user = user;
        userLocation.location = location;
        userLocation.name = name;
        userLocation.phone = phone;
        userLocation.locationNick = locationNick;
        return userLocation;
    }

    public void update(UpdateUserLocationCommand command) {
        if (command.getLocation() != null) {
            this.location = command.getLocation();
        }
        if (command.getName() != null) {
            this.name = command.getName();
        }
        if (command.getPhone() != null) {
            this.phone = command.getPhone();
        }
        if (command.getLocationNick() != null) {
            this.locationNick = command.getLocationNick();
        }
    }
}
