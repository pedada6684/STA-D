package com.klpc.stadspring.domain.cart.service.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCartProductListCommand {
    List<GetCartProductCommand> cartProductList;
}
