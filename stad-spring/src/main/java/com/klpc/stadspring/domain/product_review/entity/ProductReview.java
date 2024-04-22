package com.klpc.stadspring.domain.product_review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_review_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "score")
    private Double score;

    @Column(name = "review_img")
    private String reviewImg;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    public static ProductReview createNewReview(
          User user,
          Product product,
          String title,
          String content,
          Double score,
          String reviewImg,
          String regDate
    ) {
        ProductReview review = new ProductReview();
        review.user = user;
        review.product = product;
        review.title = title;
        review.content = content;
        review.score = score;
        review.reviewImg = reviewImg;
        review.regDate = LocalDateTime.now();
        return review;
    }

}
