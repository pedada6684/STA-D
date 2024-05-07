package com.klpc.stadspring.domain.productType.service.command;

import lombok.Getter;

@Getter
public class GetProductTypeListByUserIdCommand {

    Long productTypeId;
    String productImgUrl;
    String name;
    Long price;
    Long quantity;
    Long cnt;

    public GetProductTypeListByUserIdCommand(Long productTypeId, String productImgUrl, String name, Long price, Long quantity, Long cnt) {
        this.productTypeId = productTypeId;
        this.productImgUrl = productImgUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.cnt = cnt;
    }
}
