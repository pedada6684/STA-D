package com.klpc.stadspring.domain.product.controller.response;

import com.klpc.stadspring.domain.product.service.command.GetProductInfoCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductListByAdverseResponse {
    /**
     * 광고 내 상품 리스트 조회
     */
    List<GetProductInfoCommand> productList;
}
