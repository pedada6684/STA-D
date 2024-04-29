package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class WithdrawUserCommand {
    Long userId;
}