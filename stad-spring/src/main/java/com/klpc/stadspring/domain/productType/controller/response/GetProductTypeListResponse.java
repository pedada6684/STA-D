package com.klpc.stadspring.domain.productType.controller.response;

import com.klpc.stadspring.domain.option.controller.response.GetOptionListByProductIdResponse;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductTypeListResponse {
    List<ProductType> list;

    public static GetProductTypeListResponse from(List<ProductType> types) {
        return GetProductTypeListResponse.builder().
                list(types).
                build();
    }
}
