package com.klpc.stadspring.domain.product_review.service.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class GetProductReviewListCommand {
    List<ProductReviewInfoCommand> list;
}
