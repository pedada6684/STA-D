package com.klpc.stadspring.domain.product.service;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.image.product_image.entity.ProductImage;
import com.klpc.stadspring.domain.image.product_image.repository.ProductImageRepository;
import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.option.repository.OptionRepository;
import com.klpc.stadspring.domain.product.controller.request.*;
import com.klpc.stadspring.domain.product.controller.response.GetProductInfoOptionResponse;
import com.klpc.stadspring.domain.product.controller.response.GetProductInfoProductTypeResponse;
import com.klpc.stadspring.domain.product.controller.response.GetProductInfoResponse;
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
import org.springframework.transaction.annotation.Transactional;

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
    public GetProductInfoResponse getProductInfo(Long productId){
        Product product = productRepository.getProductInfo(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<String> images = new ArrayList<>();
        for(ProductImage image : product.getImages()){
            images.add(image.getImg());
        }

        List<GetProductInfoProductTypeResponse> productTypeList = new ArrayList<>();
        for(ProductType productType : product.getProductType()){
            List<GetProductInfoOptionResponse> options = new ArrayList<>();
            for(ProductOption option : productType.getProductOptions()){
                GetProductInfoOptionResponse pto = GetProductInfoOptionResponse.builder()
                        .optionId(option.getId())
                        .optionName(option.getName())
                        .optionValue(option.getValue())
                        .build();
                options.add(pto);
            }

            GetProductInfoProductTypeResponse pt = GetProductInfoProductTypeResponse.builder()
                    .productTypeId(productType.getId())
                    .productTypeName(productType.getName())
                    .productTypePrice(productType.getPrice())
                    .productTypeQuantity(productType.getQuantity())
                    .options(options)
                    .build();
            productTypeList.add(pt);
        }

        GetProductInfoResponse response = GetProductInfoResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .images(images)
                .thumbnail(product.getThumbnail())
                .cityDeliveryFee(product.getCityDeliveryFee())
                .mtDeliveryFee(product.getMtDeliveryFee())
                .expStart(product.getExpStart().toLocalDate().toString())
                .expEnd(product.getExpEnd().toLocalDate().toString())
                .productTypeList(productTypeList)
                .build();

        return response;
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

    @Transactional(readOnly = false)
    public String modifyProduct(ModifyProductRequest request) {
        log.info("상품 수정 Service"+"\n"+"ProductPostRequest-productId: "+request.getProductId());
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        product.modifyProduct(
                request.getThumbnail(),
                request.getName(),
                request.getCityDeliveryFee(),
                request.getMtDeliveryFee(),
                request.getExpStart(),
                request.getExpEnd()
        );
        if(!product.getImages().isEmpty())
            productImageRepository.deleteAll(product.getImages());
        for(String img : request.getImgs()){
            ProductImage productImage = ProductImage.createNewProductImage(img, product);
            productImageRepository.save(productImage);
        }
        //productType 삭제
        for(ProductType productType : product.getProductType()){
            log.info("productTypeId : "+productType.getId()+" productTypeStatus : "+productType.getStatus());
            boolean flag = true;
            for(ModifyProductRequestProductType mpt : request.getProductTypeList()){
                log.info("mpt productTypeId : "+mpt.getProductTypeId());
                if(productType.getId().equals(mpt.getProductTypeId())){
                    flag=false;
                    break;
                }
            }
            if(flag)
                productType.deleteProductType(productType);
            log.info("productTypeId : "+productType.getId()+" productTypeStatus : "+productType.getStatus());
        }
        //productType & option 등록 및 수정
        for(ModifyProductRequestProductType pt : request.getProductTypeList()){
            //수정
            if(pt.getProductTypeId()!=-1){
                ProductType productType = productTypeRepository.findById(pt.getProductTypeId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
                log.info("productType.getName(): "+ productType.getName());
                productType.modifyProductType(pt.getProductTypeName(),pt.getProductTypePrice(),pt.getProductTypeQuantity());
                log.info("After productType.getName(): "+ productType.getName());
                //option 삭제
                for(ProductOption option : productType.getProductOptions()){
                    boolean flag = true;
                    for(ModifyProductRequestOption mpo : pt.getOptions()){
                        if(option.getId().equals(mpo.getOptionId())){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        optionRepository.delete(option);
                }
                //option 등록 및 수정
                for(ModifyProductRequestOption mpo : pt.getOptions()){
                    //수정
                    if(mpo.getOptionId()!=-1){
                        ProductOption productOption = optionRepository.findById(mpo.getOptionId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
                        productOption.modifyOption(mpo.getOptionName(),mpo.getOptionValue());
                    }
                    //등록
                    else{
                        ProductOption po = ProductOption.createNewOption(
                                productType,
                                mpo.getOptionName(),
                                mpo.getOptionValue()
                        );
                        optionRepository.save(po);
                    }
                }
            }
            //등록
            else {
                ProductType productTypeCase = ProductType.createNewProductType(
                        product,
                        pt.getProductTypeName(),
                        pt.getProductTypePrice(),
                        pt.getProductTypeQuantity()
                );

                ProductType productType = productTypeRepository.save(productTypeCase);
                for (ModifyProductRequestOption ot : pt.getOptions()) {
                    ProductOption po = ProductOption.createNewOption(
                            productType,
                            ot.getOptionName(),
                            ot.getOptionValue()
                    );
                    optionRepository.save(po);
                }
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


//    public Product updateProductInfo(UpdateProductInfoCommand command) {
//        log.info("UpdateUserInfoCommand: " + command);
//        log.info("id: "+command.getId());
//        Product product = getProductInfo(command.getId());
//        product.update(command);
//
//        productRepository.save(product);
//        return product;
//    }
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
