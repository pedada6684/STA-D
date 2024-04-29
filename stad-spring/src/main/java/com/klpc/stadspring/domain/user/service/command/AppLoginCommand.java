package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AppLoginCommand {
    String email;
    String phone;
    String nickname;
    String profile;
    String googleAT;

    public JoinUserCommand convertToJoinUserCommand(){
        return JoinUserCommand.builder()
                .email(email)
                .name("임시이름")
                .profileImage(profile)
                .nickname(nickname)
                .googleAT(googleAT)
                .build();
    }
}