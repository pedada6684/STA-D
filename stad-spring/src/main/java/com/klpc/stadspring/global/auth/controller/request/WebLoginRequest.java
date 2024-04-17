package com.klpc.stadspring.global.auth.controller.request;

import com.klpc.stadspring.domain.user.service.command.AppLoginCommand;
import com.klpc.stadspring.domain.user.service.command.WebLoginCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebLoginRequest {
    String email;
    String password;

    public WebLoginCommand toCommand(){
        return WebLoginCommand.builder()
                .password(password)
                .email(email)
                .build();
    }
}
