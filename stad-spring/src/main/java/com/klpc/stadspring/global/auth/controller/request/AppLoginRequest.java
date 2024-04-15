package com.klpc.stadspring.global.auth.controller.request;

import com.klpc.stadspring.domain.user.service.command.AppLoginCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppLoginRequest {
    String nickname;
    String name;
    String email;
    String profileImage;

    public AppLoginCommand toCommand(){
        return AppLoginCommand.builder()
                .nickname(nickname)
                .name(name)
                .email(email)
                .profileImage(profileImage)
                .build();
    }
}
