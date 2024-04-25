package com.klpc.stadspring.domain.productType.controller.request;

import com.klpc.stadspring.domain.productType.service.command.DeleteProductTypeCommand;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteProductTypeRequest {
    Long productId;
    Long id;

    public DeleteProductTypeCommand toCommand() {
        return DeleteProductTypeCommand.builder()
                .productId(productId)
                .id(id)
                .build();
    }
}
