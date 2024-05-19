package com.klpc.stadspring.domain.product.controller.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ModifyProductRequestOption {

    Long id;
    Long optionId;
    String optionName;
    Long optionValue;

}
