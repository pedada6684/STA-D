package com.klpc.stadspring.domain.product_review.service;

import com.klpc.stadspring.domain.cart.controller.response.GetCartProductListResponse;
import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.cart.service.command.GetCartProductCommand;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.product.service.command.DeleteProductCommand;
import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewListResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.product_review.repository.ProductReviewRepository;
import com.klpc.stadspring.domain.product_review.service.command.*;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import com.klpc.stadspring.util.S3Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final S3Util s3Util;

    public ProductReview getProductReviewInfo(Long productReviewId){
        ProductReview productReview = productReviewRepository.findById(productReviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return productReview;
    }

    @Transactional
    public ProductReview registReview(AddReviewCommand command) {
        log.info("ProductReview: "+command);
        Product product = productRepository.findById(command.getProductId())
                .orElseThrow(NullPointerException::new);
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(NullPointerException::new);

        URL reviewImgUrl = s3Util.uploadImageToS3(command.getReviewImg(), "review", UUID.randomUUID().toString());
        Objects.requireNonNull(reviewImgUrl);

        ProductReview newReview = ProductReview.createNewReview(
                user,
                product,
                command.getTitle(),
                command.getContent(),
                command.getScore(),
                reviewImgUrl.toString(),
                command.getRegDate()
        );

        productReviewRepository.save(newReview);

        return newReview;
    }

    public void deleteReview(Long id) {
        ProductReview review = productReviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        productReviewRepository.delete(review);
    }

    public GetProductReviewListResponse getReviewListByProductId(Long productId) {

        List<ProductReview> productReviewList = productReviewRepository.getReviewListByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<ProductReviewInfoCommand> responseList = new ArrayList<>();

        for(ProductReview productReview : productReviewList){
            ProductReviewInfoCommand response = ProductReviewInfoCommand.builder()
                    .userId(productReview.getUser().getId())
                    .productId(productReview.getProduct().getId())
                    .title(productReview.getTitle())
                    .content(productReview.getContent())
                    .score(productReview.getScore())
                    .reviewImg(productReview.getReviewImg())
                    .regDate(productReview.getRegDate())
                    .build();

            responseList.add(response);
        }
        return GetProductReviewListResponse.builder().reviewList(responseList).build();
    }

    public GetProductReviewListResponse getProductReviewListByUserId(Long userId) {
        List<ProductReview> productReviewList = productReviewRepository.getReviewListByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<ProductReviewInfoCommand> responseList = new ArrayList<>();

        for(ProductReview productReview : productReviewList){
            ProductReviewInfoCommand response = ProductReviewInfoCommand.builder()
                    .userId(productReview.getUser().getId())
                    .productId(productReview.getProduct().getId())
                    .title(productReview.getTitle())
                    .content(productReview.getContent())
                    .score(productReview.getScore())
                    .reviewImg(productReview.getReviewImg())
                    .regDate(productReview.getRegDate())
                    .build();

            responseList.add(response);
        }

        return GetProductReviewListResponse.builder().reviewList(responseList).build();
    }
}
