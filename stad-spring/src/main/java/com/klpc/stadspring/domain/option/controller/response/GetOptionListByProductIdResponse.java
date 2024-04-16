package com.klpc.stadspring.domain.option.controller.response;

import com.klpc.stadspring.domain.option.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOptionListByProductIdResponse {
    List<ProductOption> list;

    public static GetOptionListByProductIdResponse from(List<ProductOption> options) {
        return GetOptionListByProductIdResponse.builder().
                list(options).
                build();
    }
}
