package com.klpc.stadspring.domain.productType.controller.response;

import com.klpc.stadspring.domain.option.controller.response.GetOptionListByProductIdResponse;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.service.command.ProductTypeInfoCommand;
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
    List<ProductTypeInfoCommand> list;

    public static GetProductTypeListResponse from(List<ProductTypeInfoCommand> types) {
        return GetProductTypeListResponse.builder().
                list(types).
                build();
    }
}
