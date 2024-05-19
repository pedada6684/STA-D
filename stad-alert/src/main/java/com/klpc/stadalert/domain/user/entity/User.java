package com.klpc.stadalert.domain.user.entity;

import com.klpc.stadalert.domain.user.service.dto.FindUserDto;
import lombok.Data;

@Data
public class User {
    private Long userId;
    private String nickname;
    private String profile;

    public static User from(FindUserDto findUser) {
        User user = new User();
        user.userId = findUser.getUserId();
        user.nickname = findUser.getNickname();
        user.profile = findUser.getProfile();
        return user;
    }
}
