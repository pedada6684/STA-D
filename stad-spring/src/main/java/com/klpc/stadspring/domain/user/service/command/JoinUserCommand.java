package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinUserCommand {
    String nickname;
    String name;
    String email;
    String profileImage;
    String googleAT;
}