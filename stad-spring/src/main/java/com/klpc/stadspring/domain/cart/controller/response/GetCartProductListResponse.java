package com.klpc.stadspring.domain.cart.controller.response;

import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.cart.service.command.GetCartProductCommand;
import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewListResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetCartProductListResponse {
    List<GetCartProductCommand> cartProductList;
}
