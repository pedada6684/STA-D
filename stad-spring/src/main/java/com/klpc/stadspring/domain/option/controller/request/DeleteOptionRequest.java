package com.klpc.stadspring.domain.option.controller.request;

import com.klpc.stadspring.domain.option.service.command.DeleteOptionCommand;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteOptionRequest {
    Long productId;
    Long id;

    public DeleteOptionCommand toCommand() {
        return DeleteOptionCommand.builder()
                .productId(productId)
                .id(id)
                .build();
    }
}
