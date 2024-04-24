package com.klpc.stadspring.domain.option.controller;

import com.klpc.stadspring.domain.option.controller.request.AddOptionRequest;
import com.klpc.stadspring.domain.option.controller.response.GetOptionListByProductIdResponse;
import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.option.service.OptionService;
import com.klpc.stadspring.domain.option.service.command.DeleteOptionCommand;
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
@Tag(name = "옵션 컨트롤러", description = "Option Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/option")
public class OptionController {

    private final OptionService optionService;

    @GetMapping("/list/{productId}")
    @Operation(summary = "특정 상품의 옵션 리스트 조회", description = "특정 광고에 속한 상품들의 리스트를 조회합니다.")
    public ResponseEntity<?> getOptionList(@PathVariable Long productId) {
        List<ProductOption> list = optionService.getOptionList(productId);

        GetOptionListByProductIdResponse response = GetOptionListByProductIdResponse.from(list);

        // 변환된 응답을 ResponseEntity에 담아 반환
        return ResponseEntity.ok(response);
    }



    @PostMapping("/regist")
    @Operation(summary = "옵션 등록", description = "옵션 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "옵션 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<?> addNewOption(@RequestBody AddOptionRequest request) {
        try {
            optionService.addProductOption(request.toCommand());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "옵션 삭제", description = "옵션 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    public ResponseEntity<?> deleteOption(DeleteOptionCommand command) {
        optionService.deleteOption(command);
        return ResponseEntity.ok().build();
    }
}
