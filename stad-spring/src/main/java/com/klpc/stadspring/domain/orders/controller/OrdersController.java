package com.klpc.stadspring.domain.orders.controller;

import com.klpc.stadspring.domain.orders.controller.request.AddOrdersRequest;
import com.klpc.stadspring.domain.orders.controller.response.AddOrdersResponse;
import com.klpc.stadspring.domain.orders.controller.response.CancelOrdersResponse;
import com.klpc.stadspring.domain.orders.controller.response.GetOrdersListResponse;
import com.klpc.stadspring.domain.orders.controller.response.GetOrdersResponse;
import com.klpc.stadspring.domain.orders.service.OrdersService;
import com.klpc.stadspring.domain.orders.service.command.request.AddOrderRequestCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "orders 컨트롤러", description = "주문 API 입니다.")
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    @Operation(summary = "주문 생성", description = "주문 생성")
    @ApiResponse(responseCode = "200", description = "주문이 생성 되었습니다.")
    public ResponseEntity<AddOrdersResponse> addOrders(@RequestBody List<AddOrdersRequest> requestList){
        log.info("주문 생성 Controller"+"\n"+"size : "+requestList.size());

        AddOrdersResponse response = null;
        for(AddOrdersRequest request : requestList) {
            AddOrderRequestCommand command = AddOrderRequestCommand.builder()
                    .userId(request.getUserId())
                    .productTypeId(request.getProductId())
                    .productTypeCnt(request.getProductCnt())
                    .contentId(request.getContentId())
                    .advertId(request.getAdvertId())
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .locationName(request.getLocationName())
                    .location(request.getLocation())
                    .locationDetail(request.getLocationDetail())
                    .locationNum(request.getLocationNum())
                    .build();
            response = ordersService.addOrders(command);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    @Operation(summary = "주문 목록 조회", description = "주문 목록 조회")
    @ApiResponse(responseCode = "200", description = "주문 목록이 조회 되었습니다.")
    public ResponseEntity<GetOrdersListResponse> getOrdersList(@RequestParam("userId") Long userId){
        log.info("주문 목록 조회 Controller"+'\n'+"userId : "+userId);
        GetOrdersListResponse response = ordersService.getOrdersList(userId);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "주문 상세 조회", description = "주문 상세 조회")
    @ApiResponse(responseCode = "200", description = "주문이 상세 조회 되었습니다.")
    public ResponseEntity<GetOrdersResponse> getOrders(@RequestParam("ordersId") Long ordersId){
        log.info("주문 상세 조회 Controller"+"\n"+"ordersId : "+ordersId);

        GetOrdersResponse response = ordersService.getOrders(ordersId);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/cancel")
    @Operation(summary = "주문 취소", description = "주문 취소")
    @ApiResponse(responseCode = "200", description = "주문이 취소 되었습니다.")
    public ResponseEntity<CancelOrdersResponse> cancelOrders(@RequestParam("ordersId") Long ordersId){
        log.info("주문 취소 Controller"+"\n"+"ordersId : "+ordersId);

        CancelOrdersResponse response = ordersService.cancelOrders(ordersId);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
