package com.klpc.stadspring.domain.image.product_image.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AddProductImgResponse {

    List<String> data;

}
