package com.klpc.stadspring.domain.product_review.service;

import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.product.service.command.DeleteProductCommand;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.product_review.repository.ProductReviewRepository;
import com.klpc.stadspring.domain.product_review.service.command.AddReviewCommand;
import com.klpc.stadspring.domain.product_review.service.command.DeleteReviewCommand;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
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

        ProductReview newReview = ProductReview.createNewReview(
                user,
                product,
                command.getTitle(),
                command.getContent(),
                command.getRegDate()
        );

        productReviewRepository.save(newReview);

        return newReview;
    }

    public void deleteReview(DeleteReviewCommand command) {
        log.info("DeleteReviewCommand: "+command);
        ProductReview review = productReviewRepository.findById(command.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        productReviewRepository.delete(review);
    }

    public List<ProductReview> getReviewListByProductId(Long productId) {

        List<ProductReview> productReviewList = productReviewRepository.getReviewListByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return productReviewList;
    }
}