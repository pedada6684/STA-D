package com.klpc.stadspring.domain.cart.service;

import com.klpc.stadspring.domain.cart.entity.Cart;
import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.cart.repository.CartProductRepository;
import com.klpc.stadspring.domain.cart.repository.CartRepository;
import com.klpc.stadspring.domain.cart.service.command.AddProductToCartCommand;
import com.klpc.stadspring.domain.cart.service.command.DeleteProductInCartCommand;
import com.klpc.stadspring.domain.cart.service.command.UpdateCartProductCountCommand;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.product_review.service.command.DeleteReviewCommand;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Cart addProductToCart(AddProductToCartCommand command) {
        log.info("AddProductInCartCommand: "+command);
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        Cart cart = cartRepository.findByUserId(command.getUserId())
                .orElseGet(() -> createCartForUser(user));
        Product product = productRepository.findById(command.getProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        CartProduct newCartProduct = CartProduct.createNewCartProduct(
                cart,
                product,
                command.getQuantity(),
                command.getAdverseId(),
                command.getContentId()
        );
        cartProductRepository.save(newCartProduct);
        return cart;
    }

    private Cart createCartForUser(User user) {
        Cart newCart = Cart.builder().user(user).build();
        return cartRepository.save(newCart);
    }

    public void deleteCartProduct(DeleteProductInCartCommand command) {
        log.info("DeleteProductInCartCommand: "+command);
        CartProduct cartProduct = cartProductRepository.findById(command.getCartProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        cartProductRepository.delete(cartProduct);
    }

    public List<CartProduct> getCartProductListByCartId(Long cartId) {

        List<CartProduct> cartProductList = cartRepository.getCartProductByCartId(cartId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return cartProductList;
    }

    public CartProduct getCartProduct(Long cartProductId){
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        return cartProduct;
    }

    public CartProduct updateCartProductCount(UpdateCartProductCountCommand command) {
        log.info("UpdateCartProductCountCommand: " + command);

        CartProduct cartProduct = getCartProduct(command.getCartProductId());
        cartProduct.setQuantity(command.getQuantity());

        cartProductRepository.save(cartProduct);

        return cartProduct;
    }
}
