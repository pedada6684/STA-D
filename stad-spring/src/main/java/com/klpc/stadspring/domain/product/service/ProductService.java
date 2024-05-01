package com.klpc.stadspring.domain.product.service;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import com.klpc.stadspring.domain.image.product_image.repository.ProductImageRepository;
import com.klpc.stadspring.domain.product.controller.response.GetProductListByAdverseResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.product.service.command.*;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.klpc.stadspring.util.S3Util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final AdvertRepository advertRepository;
    private final S3Util s3Util;

    // 상품 리스트
//    @Override
//    public Optional<GetProductListByAdverseResponse> getAllProductByAdverseId(GetProductListByAdverseResponse command) {
//        log.info("getAllProductByAdverseId: " + command);
//        return productRepository.getAllProductByAdverseId(command.getList());
//    }

    public GetProductListByAdverseResponse getProductListByAdverseId(Long advertId) {

        List<Product> productList = productRepository.getProductListByAdverseId(advertId)
            .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetProductInfoCommand> responseList = new ArrayList<>();

        for(Product product : productList){

            List<String> productImageList = new ArrayList<>();
            for(ProductImage i : product.getImages())
                productImageList.add(i.getImg());

            GetProductInfoCommand response = GetProductInfoCommand.builder()
                    .id(product.getId())
                    .images(productImageList)
                    .thumbnail(product.getThumbnail())
                    .cityDeliveryFee(product.getCityDeliveryFee())
                    .mtDeliveryFee(product.getMtDeliveryFee())
                    .expStart(product.getExpStart().toString())
                    .expEnd(product.getExpEnd().toString())
                    .build();

            responseList.add(response);
        }
        return GetProductListByAdverseResponse.builder().productList(responseList).build();
    }

    // 상품 상세 정보
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
    public Product addProduct(AddProductCommand command) {
        log.info("AddProductCommand: "+command);

        Advert advert = advertRepository.findById(command.getAdvertId())
                .orElseThrow(NullPointerException::new);

        URL thumbnailUrl = s3Util.uploadImageToS3(command.getThumbnail(), "product_thumbnail", UUID.randomUUID().toString());
        Objects.requireNonNull(thumbnailUrl);

        Product newProduct = Product.createNewProduct(
                advert,
                thumbnailUrl.toString(),
                command.getCityDeliveryFee(),
                command.getMtDeliveryFee(),
                command.getExpStart(),
                command.getExpEnd()
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

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
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
