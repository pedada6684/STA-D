package com.klpc.stadspring.domain.product.service;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import com.klpc.stadspring.domain.image.product_image.repository.ProductImageRepository;
import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.option.repository.OptionRepository;
import com.klpc.stadspring.domain.product.controller.request.AddProductRequest;
import com.klpc.stadspring.domain.product.controller.request.AddProductRequestOption;
import com.klpc.stadspring.domain.product.controller.request.AddProductRequestProductType;
import com.klpc.stadspring.domain.product.controller.response.GetProductListByAdvertResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.product.service.command.*;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.repository.ProductTypeRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AdvertRepository advertRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductTypeRepository productTypeRepository;
    private final OptionRepository optionRepository;

    // 상품 리스트
//    @Override
//    public Optional<GetProductListByAdverseResponse> getAllProductByAdverseId(GetProductListByAdverseResponse command) {
//        log.info("getAllProductByAdverseId: " + command);
//        return productRepository.getAllProductByAdverseId(command.getList());
//    }

    public GetProductListByAdvertResponse getProductListByAdvertId(Long advertId) {

        List<Product> productList = productRepository.getProductListByAdvertId(advertId)
            .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetProductInfoCommand> responseList = new ArrayList<>();

        for(Product product : productList){

            List<String> productImageList = new ArrayList<>();
            for(ProductImage i : product.getImages())
                productImageList.add(i.getImg());

            GetProductInfoCommand response = GetProductInfoCommand.builder()
                    .id(product.getId())
                    .images(productImageList)
                    .name(product.getName())
                    .thumbnail(product.getThumbnail())
                    .cityDeliveryFee(product.getCityDeliveryFee())
                    .mtDeliveryFee(product.getMtDeliveryFee())
                    .expStart(product.getExpStart().toString())
                    .expEnd(product.getExpEnd().toString())
                    .build();

            responseList.add(response);
        }
        return GetProductListByAdvertResponse.builder().productList(responseList).build();
    }

    // 상품 상세 정보
    public Product getProductInfo(Long productId){
        Product product = productRepository.getProductInfo(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        // 이미지를 초기화
        if (product.getImages() != null) {
            product.getImages().size();
        }
        return product;
    }

    /**
     * 상품 등록
     * @param request : product, productImg, productType, option
     * @return
     */
    public String addProduct(AddProductRequest request) {
        log.info("상품 등록 Service"+"\n"+"ProductPostRequest-advertId: "+request.getAdvertId());
        Advert advert = advertRepository.findById(request.getAdvertId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Product productCase = Product.createNewProduct(
                advert,
                request.getThumbnail(),
                request.getName(),
                request.getCityDeliveryFee(),
                request.getMtDeliveryFee(),
                request.getExpStart(),
                request.getExpEnd()
        );

        Product product = productRepository.save(productCase);

        for(String imgUrl : request.getImgs()) {
            ProductImage productImage = ProductImage.createNewProductImage(imgUrl,product);
            productImageRepository.save(productImage);
        }

        for(AddProductRequestProductType pt : request.getProductTypeList()){
            ProductType productTypeCase = ProductType.createNewProductType(
                    product,
                    pt.getProductTypeName(),
                    pt.getProductTypePrice(),
                    pt.getProductTypeQuantity()
            );

            ProductType productType = productTypeRepository.save(productTypeCase);
            for(AddProductRequestOption ot : pt.getOptions()){
                ProductOption po = ProductOption.createNewOption(
                        productType,
                        ot.getOptionName(),
                        ot.getOptionValue()
                );
                optionRepository.save(po);
            }
        }

        return "success";
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        product.deleteProduct(product);

        productRepository.save(product);
    }


    public Product updateProductInfo(UpdateProductInfoCommand command) {
        log.info("UpdateUserInfoCommand: " + command);
        log.info("id: "+command.getId());
        Product product = getProductInfo(command.getId());
//        product.update(command);

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
