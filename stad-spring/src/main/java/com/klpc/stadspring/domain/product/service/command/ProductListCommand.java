package com.klpc.stadspring.domain.product.service.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ProductListCommand {
    List<GetProductInfoCommand> list;
}
