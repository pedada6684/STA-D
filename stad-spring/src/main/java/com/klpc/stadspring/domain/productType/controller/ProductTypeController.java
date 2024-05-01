package com.klpc.stadspring.domain.productType.controller;

import com.klpc.stadspring.domain.productType.controller.request.AddProductTypeRequest;
import com.klpc.stadspring.domain.productType.controller.response.GetProductTypeListResponse;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.service.ProductTypeService;
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
@Tag(name = "상품 종류 컨트롤러", description = "Product Type Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/type")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping("/list")
    @Operation(summary = "특정 상품의 타입 리스트 조회", description = "특정 상품에 속한 타입들의 리스트를 조회합니다.")
    public ResponseEntity<?> getProductTypeList(@RequestParam("productId") Long productId) {
        List<ProductType> list = productTypeService.getProductTypeList(productId);

        GetProductTypeListResponse response = GetProductTypeListResponse.from(list);

        // 변환된 응답을 ResponseEntity에 담아 반환
        return ResponseEntity.ok(response);
    }



    @PostMapping("/regist")
    @Operation(summary = "타입 등록", description = "타입 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "옵션 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<?> addNewProductType(@RequestBody AddProductTypeRequest request) {
        try {
            productTypeService.addProductType(request.toCommand());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "타입 삭제", description = "타입 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<?> deleteProductType(@RequestParam("productTypeId") Long productTypeId) {
        productTypeService.deleteProductType(productTypeId);
        return ResponseEntity.ok().build();
    }
}
