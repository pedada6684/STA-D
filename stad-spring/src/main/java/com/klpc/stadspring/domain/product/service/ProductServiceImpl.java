package com.klpc.stadspring.domain.product.service;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

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
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return product;
    }

    // 상품 등록
    @Override
    public Product addProduct(AddProductCommand command) {
        log.info("AddProductCommand: "+command);
        Product newProduct = Product.createNewProduct(
                command.getName(),
                command.getPrice(),
                command.getQuantity(),
                command.getIntroduction(),
                command.getThumbnail(),
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
