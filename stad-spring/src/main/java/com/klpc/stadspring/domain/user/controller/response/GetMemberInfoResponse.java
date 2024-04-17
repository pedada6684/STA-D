package com.klpc.stadspring.domain.user.controller.response;

import com.klpc.stadspring.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMemberInfoResponse {
    private Long userId;
    private String nickname;
    private String email;
    private String phone;
    private String company;
    private String name;
    private String comNo;
    private String profile;

    public static GetMemberInfoResponse from(User user){
        return GetMemberInfoResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .company(user.getCompany())
                .name(user.getName())
                .comNo(user.getComNo())
                .profile(user.getProfile())
                .build();
    }
}
