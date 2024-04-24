package com.klpc.stadspring.domain.option.controller.request;

import com.klpc.stadspring.domain.option.service.command.AddOptionCommand;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddOptionRequest {
    private Long productId;
    private String name;
    private String value;

    public AddOptionCommand toCommand() {
        return AddOptionCommand.builder()
                .productId(productId)
                .name(name)
                .value(value)
                .build();
    }
}
