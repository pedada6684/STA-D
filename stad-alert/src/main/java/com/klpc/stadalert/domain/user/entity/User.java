package com.klpc.stadalert.domain.user.entity;

import com.klpc.stadalert.domain.user.service.dto.FindUserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;
    private String nickname;
    private String profile;

    public static User createNewUser(FindUserDto findUser) {
        User user = new User();
        user.userId = findUser.getData().getUserId();
        user.nickname = findUser.getData().getNickname();
        user.profile = findUser.getData().getProfile();
        return user;
    }
}
