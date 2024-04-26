package com.klpc.stadspring.domain.cart.controller;

import com.klpc.stadspring.domain.cart.controller.request.AddCartProductRequest;
import com.klpc.stadspring.domain.cart.controller.request.CartProductDeleteRequest;
import com.klpc.stadspring.domain.cart.controller.request.CartProductPostRequest;
import com.klpc.stadspring.domain.cart.controller.request.UpdateCartProductCountRequest;
import com.klpc.stadspring.domain.cart.controller.response.GetCartProductInfoResponse;
import com.klpc.stadspring.domain.cart.controller.response.GetCartProductListResponse;
import com.klpc.stadspring.domain.cart.entity.CartProduct;
import com.klpc.stadspring.domain.cart.service.CartService;
import com.klpc.stadspring.domain.cart.service.command.AddProductToCartCommand;
import com.klpc.stadspring.domain.cart.service.command.DeleteProductInCartCommand;
import com.klpc.stadspring.domain.product_review.controller.response.GetProductReviewListResponse;
import com.klpc.stadspring.domain.product_review.entity.ProductReview;
import com.klpc.stadspring.domain.product_review.service.command.DeleteReviewCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "장바구니 컨트롤러", description = "Cart Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/regist")
    @Operation(summary = "장바구니 물건 등록", description = "장바구니 물건 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<?> addNewCartProduct(@RequestBody AddCartProductRequest request) {
        log.info("AddProductToCartCommand: "+ request);

        try {
            cartService.addProductToCart(request.toCommand());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니 상품 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니 상품 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<?> deleteReview(@RequestBody CartProductDeleteRequest request) {
        log.info("DeleteProductInCartCommand"+request);

        cartService.deleteCartProduct(request.toCommand());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{userId}")
    @Operation(summary = "장바구니 상품 리스트 조회", description = "장바구니 상품 리스트 조회")
    public ResponseEntity<?> getCartProductListByCartId(@PathVariable Long userId) {
        List<CartProduct> list = cartService.getCartProductListByCartId(userId);

        GetCartProductListResponse response = GetCartProductListResponse.from(list);

        // 변환된 응답을 ResponseEntity에 담아 반환
        return ResponseEntity.ok(response);
    }

    @PutMapping("/count")
    @Operation(summary = "장바구니 상품 수량 변경", description = "장바구니 상품 수량 변경")
    public ResponseEntity<?> updateCartProductCount(@RequestBody UpdateCartProductCountRequest request) {
        log.info("UpdateCartProductCountRequest: "  + request);

        CartProduct cartProduct = cartService.updateCartProductCount(request.toCommand());

        GetCartProductInfoResponse response = GetCartProductInfoResponse.from(cartProduct);
        // 변환된 응답을 ResponseEntity에 담아 반환
        return ResponseEntity.ok(response);
    }
}
