package com.klpc.stadspring.domain.user.controller.response;

import com.klpc.stadspring.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMemberInfoResponse {
    private Long id;
    private String email;
    private String nickname;
    private String username;
    private String profileUrl;

    public static GetMemberInfoResponse from(User user){
        return GetMemberInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .username(user.getName())
                .profileUrl(user.getProfile())
                .build();
    }
}
