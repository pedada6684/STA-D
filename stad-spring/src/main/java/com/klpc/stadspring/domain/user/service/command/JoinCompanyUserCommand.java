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
                .name(name)
                .nickname(name)
                .phone(phone)
                .company(company)
                .comNo(comNo)
                .department(department)
                .password(password)
                .build();
    }
}