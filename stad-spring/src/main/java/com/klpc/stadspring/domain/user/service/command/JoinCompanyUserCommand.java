package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class JoinCompanyUserCommand {
    String email;
    String name;
    String phone;
    String password;
    String company;
    String comNo;
    String department;

    public UpdateUserInfoCommand convertToUpdateCommand(){
        return UpdateUserInfoCommand.builder()
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