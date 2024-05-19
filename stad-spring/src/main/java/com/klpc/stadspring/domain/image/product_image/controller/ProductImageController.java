package com.klpc.stadspring.domain.image.product_image.controller;

import com.klpc.stadspring.domain.image.product_image.controller.response.AddProductImgResponse;
import com.klpc.stadspring.domain.image.product_image.controller.response.AddProductThumbnailResponse;
import com.klpc.stadspring.domain.image.product_image.service.ProductImageService;
import com.klpc.stadspring.domain.image.product_image.service.command.AddProductImgResponseCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product-img")
@Tag(name = "advertVideo 컨트롤러", description = "광고 영상 API 입니다.")
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping("/add-img-list")
    @Operation(summary = "상품 이미지 업로드", description = "상품 이미지 업로드")
    @ApiResponse(responseCode = "200", description = "상품 이미지가 업로드 되었습니다.")
    public ResponseEntity<AddProductImgResponse> addVideoList(@RequestPart("imgs") List<MultipartFile> imgs){
        log.info("상품 이미지 업로드"+"\n"+"imgList Size : "+imgs.size());
        AddProductImgResponse response = productImageService.addProductImg(AddProductImgResponseCommand.builder().imgs(imgs).build());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add-thumbnail")
    @Operation(summary = "상품 썸네일 추가", description = "상품 썸네일 추가")
    @ApiResponse(responseCode = "200", description = "상품 썸네일이 업로드 되었습니다.")
    public ResponseEntity<AddProductThumbnailResponse> getAdvertVideo(@RequestPart("thumbnail") MultipartFile thumbnail){
        log.info("상품 썸네일 업로드"+"\n"+"thumbnail : "+thumbnail.toString());
        AddProductThumbnailResponse response = productImageService.addProductThumbnail(thumbnail);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
