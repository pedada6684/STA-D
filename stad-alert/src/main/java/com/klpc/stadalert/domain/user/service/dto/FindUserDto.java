package com.klpc.stadalert.domain.user.service.dto;

import lombok.Data;

@Data
public class FindUserDto {
    private Long userId;
    private String nickname;
    private String email;
    private String phone;
    private String company;
    private String name;
    private String comNo;
    private String profile;
}

