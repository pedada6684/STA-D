package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AppLoginCommand {
    String nickname;
    String name;
    String email;
    String profileImage;

    public JoinUserCommand convertToJoinUserCommand(){
        return JoinUserCommand.builder()
                .email(email)
                .name(name)
                .profileImage(profileImage)
                .nickname(nickname)
                .build();
    }
}