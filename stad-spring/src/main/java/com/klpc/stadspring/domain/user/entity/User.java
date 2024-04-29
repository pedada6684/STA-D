package com.klpc.stadspring.domain.user.entity;

import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.user.service.command.UpdateUserInfoCommand;
import com.klpc.stadspring.domain.user.service.command.UpdateUserLocationCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String phone;
    private String profile = null;
    private Long type;
    private Long status; // 탈퇴 0, 정상 1
    private LocalDateTime regDate;
    private LocalDateTime delDate;
    private String company;
    private String department;
    private String comNo;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_youtube_info_id")
    private UserYoutubeInfo youtubeInfo = null;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE,  orphanRemoval = true)
    private List<CartProduct> cartProduct;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<UserLocation> userLocations = new ArrayList<>();

    public static User createNewUser(
            String email,
            String password,
            String nickname,
            String username,
            Long type
    ){
        User user = new User();
        user.email = email;
        user.password = password;
        user.nickname = nickname;
        user.name = username;
        user.type = type;
        user.status = 1L;
        user.regDate = LocalDateTime.now();
        return user;
    }

    public void withdraw(){
        this.status = 0L;
        this.delDate = LocalDateTime.now();
    }

    public void update(UpdateUserInfoCommand command) {
        if (command.getName() != null) {
            this.name = command.getName();
        }
        if (command.getNickname() != null) {
            this.nickname = command.getNickname();
        }
        if (command.getPhone() != null) {
           this.phone = command.getPhone();
        }
        if (command.getCompany() != null) {
           this.company = command.getCompany();
        }
        if (command.getComNo() != null) {
           this.comNo = command.getComNo();
        }
        if (command.getDepartment() != null) {
            this.department = command.getDepartment();
        }
    }

    public void updateProfileUrl(String profile) {
        this.profile = profile;
    }

    public void updateYoutubeInfo(String youtubeInfo){
        this.youtubeInfo = UserYoutubeInfo.createNewUserYoutubeInfo(youtubeInfo);
    }

    /**
     * userLocation 저장
     * @param userLocation: 저장할 장소
     */
    public void addUserLocation(UserLocation userLocation){
        this.userLocations.add(userLocation);
    }

    /**
     * userLocation 수정
     * @param command
     * @return
     */
    public UserLocation updateUserLocation(UpdateUserLocationCommand command){
        for (UserLocation location : userLocations) {
            if (location.getId().equals(command.getLocationId())){
                location.update(command);
                return location;
            }
        }
        throw new CustomException(ErrorCode.ENTITIY_NOT_FOUND);
    }
}
