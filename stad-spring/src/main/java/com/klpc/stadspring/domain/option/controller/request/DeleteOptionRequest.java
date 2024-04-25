package com.klpc.stadspring.domain.option.controller.request;

import com.klpc.stadspring.domain.option.service.command.DeleteOptionCommand;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteOptionRequest {
    Long productTypeId;
    Long id;

    public DeleteOptionCommand toCommand() {
        return DeleteOptionCommand.builder()
                .productTypeId(productTypeId)
                .id(id)
                .build();
    }
}
