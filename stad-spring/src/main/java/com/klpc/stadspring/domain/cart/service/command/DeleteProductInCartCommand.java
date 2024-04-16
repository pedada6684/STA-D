package com.klpc.stadspring.domain.cart.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteProductInCartCommand {
    Long cartProductId;
}
