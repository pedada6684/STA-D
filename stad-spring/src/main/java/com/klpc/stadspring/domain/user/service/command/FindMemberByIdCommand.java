package com.klpc.stadspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindMemberByIdCommand {
    Long id;
}
