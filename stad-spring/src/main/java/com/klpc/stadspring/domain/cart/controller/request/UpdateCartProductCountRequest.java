package com.klpc.stadspring.domain.cart.controller.request;

import com.klpc.stadspring.domain.cart.service.command.UpdateCartProductCountCommand;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCartProductCountRequest {
    Long cartProductId;
    Long quantity;

    public UpdateCartProductCountCommand toCommand() {
        return UpdateCartProductCountCommand.builder().
                cartProductId(cartProductId).
                quantity(quantity).
                build();
    }
}
