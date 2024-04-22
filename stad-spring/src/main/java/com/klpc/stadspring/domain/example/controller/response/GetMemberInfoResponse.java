package com.klpc.stadspring.domain.example.controller.response;

import com.klpc.stadspring.domain.example.entity.Member;
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

    public static GetMemberInfoResponse from(Member member){
        return GetMemberInfoResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .username(member.getUsername())
                .profileUrl(member.getProfileUrl())
                .build();
    }
}
