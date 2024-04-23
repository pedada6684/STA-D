package com.klpc.stadspring.domain.product.service;

import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import com.klpc.stadspring.domain.image.product_image.repository.ProductImageRepository;
import com.klpc.stadspring.domain.product.controller.response.GetProductListByAdverseResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.product.service.command.AddProductCommand;
import com.klpc.stadspring.domain.product.service.command.DeleteProductCommand;
import com.klpc.stadspring.domain.product.service.command.GetProductListByAdverseCommand;
import com.klpc.stadspring.domain.product.service.command.UpdateProductInfoCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.klpc.stadspring.util.S3Util;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final S3Util s3Util;

    // 상품 리스트
//    @Override
//    public Optional<GetProductListByAdverseResponse> getAllProductByAdverseId(GetProductListByAdverseResponse command) {
//        log.info("getAllProductByAdverseId: " + command);
//        return productRepository.getAllProductByAdverseId(command.getList());
//    }

    @Override
    public List<Product> getProductListByAdverseId(Long adverseId) {

        List<Product> productList = productRepository.getProductListByAdverseId(adverseId)
            .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return productList;
    }

    // 상품 상세 정보
    @Override
    public Product getProductInfo(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        // 이미지를 초기화
        if (product.getImages() != null) {
            product.getImages().size();
        }
        return product;
    }

    // 상품 등록
    @Override
    public Product addProduct(AddProductCommand command) {
        log.info("AddProductCommand: "+command);

        URL thumbnailUrl = s3Util.uploadImageToS3(command.getThumbnail(), "product_thumbnail", UUID.randomUUID().toString());
        Objects.requireNonNull(thumbnailUrl);



        Product newProduct = Product.createNewProduct(
                command.getName(),
                command.getPrice(),
                command.getQuantity(),
                thumbnailUrl.toString(),
                command.getCategory(),
                command.getSellStart(),
                command.getSellEnd(),
                command.getCityDeliveryFee(),
                command.getMtDeliveryFee(),
                command.getExpStart(),
                command.getExpEnd(),
                command.getDeliveryDate()
        );

        productRepository.save(newProduct);

        command.getImages().forEach(image -> {
            URL imageUrl = s3Util.uploadImageToS3(image, "product_introduction", UUID.randomUUID().toString());
            if (imageUrl != null) {
                ProductImage productImage = new ProductImage(null, imageUrl.toString(), newProduct);
                productImageRepository.save(productImage);
            }
        });

        return newProduct;
    }

    public void deleteProduct(DeleteProductCommand command) {
        log.info("DeleteProductCommand: "+command);
        Product product = productRepository.findById(command.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        productRepository.delete(product);
    }


    public Product updateProductInfo(UpdateProductInfoCommand command) {
        log.info("UpdateUserInfoCommand: " + command);
        log.info("id: "+command.getId());
        Product product = getProductInfo(command.getId());
        product.update(command);

        productRepository.save(product);
        return product;
    }
//    @Override
//    public Long registProduct(Long adverseId, Long OrderId, ProductPostDto productPostDto){
//        Product product =
//
//        /**
//        *  밑에 잘 넣음시다  광고 아이디로 광고 찾아서
//        **/
//
//        product = productRepository.save(product);
//        return product.getId();
//    }
}
