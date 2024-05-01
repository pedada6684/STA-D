package com.klpc.stadspring.domain.product.controller;

import com.klpc.stadspring.domain.image.product_image.service.ProductImageService;
import com.klpc.stadspring.domain.product.controller.request.ProductPostRequest;
import com.klpc.stadspring.domain.product.controller.response.GetProductInfoResponse;
import com.klpc.stadspring.domain.product.controller.response.GetProductListByAdverseResponse;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.controller.request.UpdateProductInfoRequest;
import com.klpc.stadspring.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "상품 컨트롤러", description = "Product Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductImageService productImageService;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "상품 정보 요청", description = "상품 정보 요청")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GetProductInfoResponse.class)))
    public ResponseEntity<GetProductInfoResponse> getProductInfo(@RequestParam("id") Long id) {
        Product product = productService.getProductInfo(id);
        GetProductInfoResponse response = GetProductInfoResponse.from(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/regist", consumes = "multipart/form-data", produces = "application/json")
    @Operation(summary = "상품 등록", description = "상품 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<?> addNewProduct(@ModelAttribute ProductPostRequest request) {
        log.info("ProductPostRequest: "+request);
        try {
            productService.addProduct(request.toCommand());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 상품 수정


    // 상품 삭제

    /**
     *
     * @param command
     * @return
     */
    @DeleteMapping("/delete")
    @Operation(summary = "상품 삭제", description = "상품 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<?> deleteProduct(@RequestParam("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/list/{id}")
//    @Operation(summary = "상품 리스트 요청", description = "상품 리스트 요청")
//    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GetProductListByAdverseResponse.class)))
//    public ResponseEntity<?> getProductListByAdverseId(@RequestParam("id") Long id) {
//        Optional<> list = productService.getAllProductByAdverseId(id);
//        GetProductListResponse response = GetProductInfoResponse;
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    // 상품 수정

    /**
     *
     * @param request
     * @return
     */
    @PutMapping("/update")
    @Operation(summary = "상품 정보 변경", description = "상품 정보 변경")
    @ApiResponse(responseCode = "200", description = "상품 정보 수정 성공")
    public ResponseEntity<?> updateProductInfo(@RequestBody UpdateProductInfoRequest request) {
        log.info("UpdateProductInfoRequest: " + request);
        Product product = productService.updateProductInfo(request.toCommand());
        log.info("Post: "+ product);
        GetProductInfoResponse response = GetProductInfoResponse.from(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param advertId
     * @return
     *
     *
     */
    @GetMapping("/list")
    @Operation(summary = "특정 광고에 속한 상품 리스트 조회", description = "특정 광고에 속한 상품들의 리스트를 조회합니다.")
    public ResponseEntity<?> getProductListByAdverseId(@RequestParam Long advertId) {
        GetProductListByAdverseResponse response = productService.getProductListByAdverseId(advertId);

        // 변환된 응답을 ResponseEntity에 담아 반환
        return ResponseEntity.ok(response);
    }

    /**
     *
     * @param productId
     * @param file
     * @return
     */
    @PostMapping(value = "/{productId}/image", consumes = "multipart/form-data", produces = "application/json")
    @Operation(summary = "사진만 등록(수정 시)", description = "사진만 등록(수정시)")
    public ResponseEntity<?> addImage(@PathVariable Long productId, @RequestParam("image") MultipartFile file) {
        productImageService.addImage(productId, file);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * @param imageId
     * @return
     */
    @DeleteMapping("/image/delete")
    @Operation(summary = "사진만 삭제(수정시)", description = "사진만 삭제(수정시)")
    public ResponseEntity<?> deleteImage(@RequestParam("imageId") Long imageId) {
        productImageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}
