package com.klpc.stadspring.domain.product.controller.response;

import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.service.command.GetProductListByAdverseCommand;
import com.klpc.stadspring.domain.user.controller.response.GetMemberInfoResponse;
import com.klpc.stadspring.domain.user.entity.User;
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
    List<Product> productList;

    public static GetProductListByAdverseResponse from(List<Product> list){
        return GetProductListByAdverseResponse.builder()
                .productList(list)
                .build();
    }
}
