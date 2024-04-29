package com.klpc.stadspring.global.auth.controller.request;

import com.klpc.stadspring.domain.user.service.command.JoinCompanyUserCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebJoinRequest {
    String email;
    String phone;
    String name;
    String password;
    String company;
    String comNo;
    String department;

    public JoinCompanyUserCommand toCommand(){
        return JoinCompanyUserCommand.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .password(password)
                .company(company)
                .comNo(comNo)
                .department(department)
                .build();
    }
}
