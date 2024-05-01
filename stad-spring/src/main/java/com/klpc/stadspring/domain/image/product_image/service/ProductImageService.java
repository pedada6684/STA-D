package com.klpc.stadspring.domain.image.product_image.service;

import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import com.klpc.stadspring.domain.image.product_image.repository.ProductImageRepository;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.global.response.exception.CustomException;
import com.klpc.stadspring.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final S3Util s3Util;

    public void addImage(Long productId, MultipartFile file) {
        URL imageUrl = s3Util.uploadImageToS3(file, "product_introduction", UUID.randomUUID().toString());
        Objects.requireNonNull(imageUrl);

        Product product = productRepository.findById(productId)
                .orElseThrow(NullPointerException::new);

        ProductImage productImage = ProductImage.createNewProductImage(imageUrl.toString(), product);
        imageRepository.save(productImage);
    }

    public void deleteImage(Long imageId) {
        ProductImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // 데이터베이스에서 이미지 정보만 삭제
        imageRepository.delete(image);
    }
}
