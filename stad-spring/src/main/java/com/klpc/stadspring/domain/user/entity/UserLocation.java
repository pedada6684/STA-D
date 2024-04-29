package com.klpc.stadspring.domain.user.entity;

import com.klpc.stadspring.domain.user.service.command.UpdateUserInfoCommand;
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

//    public void update(UpdateUserLoctionCommand command) {
//        if (command.getName() != null) {
//            this.name = command.getName();
//        }
//        if (command.getNickname() != null) {
//            this.nickname = command.getNickname();
//        }
//        if (command.getPhone() != null) {
//           this.phone = command.getPhone();
//        }
//        if (command.getCompany() != null) {
//           this.company = command.getCompany();
//        }
//        if (command.getComNo() != null) {
//           this.comNo = command.getComNo();
//        }
//        if (command.getDepartment() != null) {
//            this.department = command.getDepartment();
//        }
//    }
}
