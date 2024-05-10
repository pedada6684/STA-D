package com.klpc.stadspring.domain.orders.service;

import com.klpc.stadspring.domain.advert.entity.Advert;
import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advertVideo.service.command.response.GetTotalLogResponse;
import com.klpc.stadspring.domain.cart.controller.response.GetCartProductInfoResponse;
import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.option.repository.OptionRepository;
import com.klpc.stadspring.domain.orderProduct.entity.OrderProduct;
import com.klpc.stadspring.domain.orderProduct.repository.OrderProductRepository;
import com.klpc.stadspring.domain.orders.controller.request.AddOrdersRequest;
import com.klpc.stadspring.domain.orders.controller.response.AddOrdersResponse;
import com.klpc.stadspring.domain.orders.controller.response.CancelOrdersResponse;
import com.klpc.stadspring.domain.orders.controller.response.GetOrdersListResponse;
import com.klpc.stadspring.domain.orders.controller.response.GetOrdersResponse;
import com.klpc.stadspring.domain.orders.entity.Orders;
import com.klpc.stadspring.domain.orders.repository.OrdersRepository;
import com.klpc.stadspring.domain.orders.service.command.request.AddOrderLogCommand;
import com.klpc.stadspring.domain.orders.service.command.request.AddOrderRequestCommand;
import com.klpc.stadspring.domain.orders.service.command.request.AddOrdersProductTypeRequestCommand;
import com.klpc.stadspring.domain.orders.service.command.response.GetOrderListProductTypeResponseCommand;
import com.klpc.stadspring.domain.orders.service.command.response.GetOrdersListResponseCommand;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
import com.klpc.stadspring.domain.productType.entity.ProductType;
import com.klpc.stadspring.domain.productType.repository.ProductTypeRepository;
import com.klpc.stadspring.domain.user.entity.User;
import com.klpc.stadspring.domain.user.repository.UserRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersService {

    private final UserRepository userRepository;
    private final ProductTypeRepository productTypeRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrdersRepository ordersRepository;
    private final OptionRepository optionRepository;
//    private final LogService logService;

    /**
     * 주문 생성
     * @param command
     * @return
     */
    @Transactional(readOnly = false)
    public AddOrdersResponse addOrders(AddOrderRequestCommand command){
        log.info("주문 추가 Service"+"\n"+"Command userId : "+command.getUserId());
        User user = userRepository.findById(command.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Orders orders = Orders.createToOrders(user);
        ordersRepository.save(orders);

        for(AddOrdersProductTypeRequestCommand ptCommand : command.getAddOrdersProductTypeRequestCommands()){
            ProductType productType = productTypeRepository.findById(ptCommand.getProductTypeId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
            if(ptCommand.getProductCnt()>productType.getQuantity())
                throw new CustomException(ErrorCode.QUANTITY_ERROR);
            if(ptCommand.getContentId()>0) {
                OrderProduct orderProduct = OrderProduct.createToOrderProduct(ptCommand.getProductCnt(), ptCommand.getOptionId());
                orderProduct.linkedOrders(orders);
                orderProduct.linkedProductType(productType);
                orderProductRepository.save(orderProduct);
            }
// TODO : 이 부분 카프카로 바꿀 것
            AddOrderLogCommand addOrderLogCommand = AddOrderLogCommand.builder()
                    .advertId(ptCommand.getAdvertId())
                    .userId(command.getUserId())
                    .orderId(orders.getId())
                    .contentId(ptCommand.getContentId())
                    .productId(ptCommand.getProductTypeId())
                    .price(productType.getPrice())
                    .build();

            productType.modifyQuantity(-1* ptCommand.getProductCnt());

            sendOrderLogRequest(addOrderLogCommand);
        }

        return AddOrdersResponse.builder().result("success").build();
    }

    /**
     * 주문 목록 조회
     * @param userId
     * @return
     */
    public GetOrdersListResponse getOrdersList(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<Orders> ordersByUser = ordersRepository.findAllByUser(user);
        List<GetOrdersListResponseCommand> response = new ArrayList<>();
        for(Orders orders : ordersByUser){
            List<GetOrderListProductTypeResponseCommand> productTypeList = new ArrayList<>();
            for(OrderProduct orderProduct : orders.getOrderProducts()){
                ProductType productType = orderProduct.getProductType();
                ProductOption productOption = optionRepository.findById(orderProduct.getOptionId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
                GetOrderListProductTypeResponseCommand ptCommand = GetOrderListProductTypeResponseCommand.builder()
                        .productTypeId(productType.getId())
                        .productName(productType.getName())
                        .productCnt(orderProduct.getCnt())
                        .productPrice(productType.getPrice())
                        .productImg(productType.getProduct().getThumbnail())
                        .optionId(productOption.getId())
                        .optionName(productOption.getName())
                        .optionValue(productOption.getValue())
                        .build();
                productTypeList.add(ptCommand);
            }
            GetOrdersListResponseCommand command = GetOrdersListResponseCommand.builder()
                    .ordersId(orders.getId())
                    .orderDate(orders.getOrderDate().toLocalDate().toString())
                    .orderStatus(orders.getStatus().name())
                    .productTypes(productTypeList)
                    .build();
            response.add(command);
        }
        return GetOrdersListResponse.builder().data(response).build();
    }

    /**
     * 주문 상세 조회
     * @param ordersId
     * @return
     */
    public GetOrdersResponse getOrders(Long ordersId){
        Orders orders = ordersRepository.findById(ordersId).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        List<GetOrderListProductTypeResponseCommand> productTypeList = new ArrayList<>();
        for(OrderProduct orderProduct : orders.getOrderProducts()){
            ProductType productType = orderProduct.getProductType();
            ProductOption productOption = optionRepository.findById(orderProduct.getOptionId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
            GetOrderListProductTypeResponseCommand ptCommand = GetOrderListProductTypeResponseCommand.builder()
                    .productTypeId(productType.getId())
                    .productName(productType.getName())
                    .productCnt(orderProduct.getCnt())
                    .productPrice(productType.getPrice())
                    .productImg(productType.getProduct().getThumbnail())
                    .optionId(productOption.getId())
                    .optionName(productOption.getName())
                    .optionValue(productOption.getValue())
                    .build();
            productTypeList.add(ptCommand);
        }
        GetOrdersResponse response = GetOrdersResponse.builder()
                .ordersId(orders.getId())
                .orderDate(orders.getOrderDate().toLocalDate().toString())
                .orderStatus(orders.getStatus().name())
                .productTypes(productTypeList)
                .build();

        return response;
    }

    public CancelOrdersResponse cancelOrders(Long ordersId){
        Orders orders = ordersRepository.findById(ordersId).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        orders.cancelOrders();

        return CancelOrdersResponse.builder().result("success").build();
    }

    /**
     * productType 주문량
     * @param productTypeId
     * @return
     */
    public Long cntOrdersByproductTypeId(Long productTypeId){
        Long cnt = ordersRepository.findCntOrdersByProductType(productTypeId).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return cnt;
    }

    public void sendOrderLogRequest(AddOrderLogCommand requestData) {
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity에 요청 데이터와 헤더 포함
        HttpEntity<AddOrderLogCommand> entity = new HttpEntity<>(requestData, headers);

        // POST 요청 보내기
        String url = "http://localhost:8082/stats/log/order-log";
        String response = restTemplate.postForObject(url, entity, String.class);

        // 응답 출력
        System.out.println("Response Body: " + response);
    }

}
