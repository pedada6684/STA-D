package com.klpc.stadstats.domain.tmp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetUserInfoResponse {
    private Long userId;
    private String nickname;
    private String email;
    private String phone;
    private String company;
    private String name;
    private String comNo;
    private String profile;
}
