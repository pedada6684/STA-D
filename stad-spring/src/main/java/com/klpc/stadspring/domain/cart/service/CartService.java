package com.klpc.stadspring.domain.cart.service;

import com.klpc.stadspring.domain.advert.service.command.response.GetAdvertResponseCommand;
import com.klpc.stadspring.domain.cart.controller.request.CartProductPostRequest;
import com.klpc.stadspring.domain.cart.controller.response.GetCartProductInfoResponse;
import com.klpc.stadspring.domain.cart.controller.response.GetCartProductListResponse;
import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.cart.repository.CartProductRepository;
import com.klpc.stadspring.domain.cart.service.command.AddProductToCartCommand;
import com.klpc.stadspring.domain.cart.service.command.DeleteProductInCartCommand;
import com.klpc.stadspring.domain.cart.service.command.GetCartProductCommand;
import com.klpc.stadspring.domain.cart.service.command.UpdateCartProductCountCommand;
import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.option.repository.OptionRepository;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.repository.ProductTypeRepository;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;
    private final ProductTypeRepository productTypeRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public List<CartProduct> addProductToCart(AddProductToCartCommand command) {
        log.info("AddProductInCartCommand: " + command);

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<CartProduct> addedProducts = new ArrayList<>();

        for (CartProductPostRequest cartProductPostRequest : command.getCartProductList()) {
            ProductType productType = productTypeRepository.findById(cartProductPostRequest.getProductTypeId())
                    .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

            CartProduct newCartProduct = CartProduct.createNewCartProduct(
                    productType,
                    user,
                    cartProductPostRequest.getQuantity(),
                    cartProductPostRequest.getAdvertId(),
                    cartProductPostRequest.getContentId(),
                    cartProductPostRequest.getOptionId()
                    );
            cartProductRepository.save(newCartProduct);
            addedProducts.add(newCartProduct);
        }

        return addedProducts;
    }

    public void deleteCartProduct(DeleteProductInCartCommand command) {
        log.info("DeleteProductInCartCommand: "+command);
        CartProduct cartProduct = cartProductRepository.findById(command.getCartProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        cartProductRepository.delete(cartProduct);
    }

    public GetCartProductListResponse getCartProductListByUserId(Long userId) {

        List<CartProduct> cartProductList = cartProductRepository.getCartProductByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetCartProductCommand> responseList = new ArrayList<>();

        for(CartProduct cartProduct : cartProductList){

            ProductOption productOption;

            if(cartProduct.getOptionId() != -1) {
                productOption = optionRepository.findById(cartProduct.getOptionId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
            } else {
                productOption = null;
            }

            GetCartProductCommand response = GetCartProductCommand.builder()
                    .cartProductId(cartProduct.getId())
                    .productId(cartProduct.getProductType().getProduct().getId())
                    .productName(cartProduct.getProductType().getProduct().getName())
                    .productType(cartProduct.getProductType())
                    .quantity(cartProduct.getQuantity())
                    .advertId(cartProduct.getAdvertId())
                    .contentId(cartProduct.getContentId())
                    .thumbnail(cartProduct.getProductType().getProduct().getThumbnail())
                    .option(productOption)
                    .build();

            responseList.add(response);
        }
        return GetCartProductListResponse.builder().cartProductList(responseList).build();
    }

    public GetCartProductInfoResponse getCartProduct(Long cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        ProductOption productOption = optionRepository.findById(cartProduct.getOptionId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        GetCartProductInfoResponse response = GetCartProductInfoResponse.builder()
                .cartProductId(cartProduct.getId())
                .productId(cartProduct.getProductType().getProduct().getId())
                .productName(cartProduct.getProductType().getProduct().getName())
                .productType(cartProduct.getProductType())
                .quantity(cartProduct.getQuantity())
                .advertId(cartProduct.getAdvertId())
                .contentId(cartProduct.getContentId())
                .thumbnail(cartProduct.getProductType().getProduct().getThumbnail())
                .option(productOption)
                .build();

        return response;
    }

    public CartProduct getCartProductOne(Long cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        return cartProduct;
    }

    public GetCartProductInfoResponse updateCartProductCount(UpdateCartProductCountCommand command) {
        log.info("UpdateCartProductCountCommand: " + command);

        CartProduct cartProduct = getCartProductOne(command.getCartProductId());
        cartProduct.setQuantity(command.getQuantity());

        cartProductRepository.save(cartProduct);

        GetCartProductInfoResponse response = GetCartProductInfoResponse.builder()
                .cartProductId(cartProduct.getId())
                .productId(cartProduct.getProductType().getProduct().getId())
                .productType(cartProduct.getProductType())
                .quantity(cartProduct.getQuantity())
                .advertId(cartProduct.getAdvertId())
                .contentId(cartProduct.getContentId())
                .thumbnail(cartProduct.getProductType().getProduct().getThumbnail())
                .option(null)
                .build();

        return response;
    }
}
