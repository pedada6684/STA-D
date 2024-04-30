package com.klpc.stadspring.domain.orders.service;

import com.klpc.stadspring.domain.cart.controller.response.GetCartProductInfoResponse;
import com.klpc.stadspring.domain.orderProduct.entity.OrderProduct;
import com.klpc.stadspring.domain.orderProduct.repository.OrderProductRepository;
import com.klpc.stadspring.domain.orders.controller.request.AddOrdersRequest;
import com.klpc.stadspring.domain.orders.controller.response.AddOrdersResponse;
import com.klpc.stadspring.domain.orders.controller.response.CancelOrdersResponse;
import com.klpc.stadspring.domain.orders.controller.response.GetOrdersListResponse;
import com.klpc.stadspring.domain.orders.controller.response.GetOrdersResponse;
import com.klpc.stadspring.domain.orders.entity.Orders;
import com.klpc.stadspring.domain.orders.repository.OrdersRepository;
import com.klpc.stadspring.domain.orders.service.command.request.AddOrderRequestCommand;
import com.klpc.stadspring.domain.orders.service.command.request.AddOrdersProductTypeRequestCommand;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 주문 생성
     * @param command
     * @return
     */
    @Transactional(readOnly = false)
    public AddOrdersResponse addOrders(AddOrderRequestCommand command){
        log.info("주문 추가 Service"+"\n"+"Command userId : "+command.getUserId());
        User user = userRepository.findById(command.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Orders orders = Orders.createToOrders(user, command.getContentId(), command.getAdvertId());
        ordersRepository.save(orders);

        for(AddOrdersProductTypeRequestCommand ptCommand : command.getAddOrdersProductTypeRequestCommands()){
            ProductType productType = productTypeRepository.findById(ptCommand.getProductTypeId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
            OrderProduct orderProduct = OrderProduct.createToOrderProduct(ptCommand.getProductCnt(), ptCommand.getOptionId());
            orderProduct.linkedOrders(orders);
            orderProduct.linkedProductType(productType);
            orderProductRepository.save(orderProduct);
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
            List<Long> productIdList = new ArrayList<>();
            for(OrderProduct orderProduct : orders.getOrderProducts()){
                productIdList.add(orderProduct.getProductType().getId());
            }
            List<String> productNameList = new ArrayList<>();
            for(OrderProduct orderProduct : orders.getOrderProducts()){
                productNameList.add(orderProduct.getProductType().getName());
            }
            List<String> productThumbnailUrl = new ArrayList<>();
            for(OrderProduct orderProduct : orders.getOrderProducts()){
                productNameList.add(orderProduct.getProductType().getProduct().getThumbnail());
            }
            GetOrdersListResponseCommand command = GetOrdersListResponseCommand.builder()
                    .ordersId(orders.getId())
                    .orderDate(orders.getOrderDate().toLocalDate().toString())
                    .contentId(orders.getContentId())
                    .advertId(orders.getAdvertId())
                    .productTypeId(productIdList)
                    .productTypeName(productNameList)
                    .productTypeThumbnailUrl(productThumbnailUrl)
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

        List<Long> productTypeId = new ArrayList<>();
        List<String> productTypeName = new ArrayList<>();
        String productThumbnailUrl = "";
        for(OrderProduct ops : orders.getOrderProducts()){
            productTypeId.add(ops.getProductType().getId());
            productTypeName.add(ops.getProductType().getName());
            productThumbnailUrl=(ops.getProductType().getProduct().getThumbnail());
        }

        GetOrdersResponse response = GetOrdersResponse.builder()
                .ordersId(orders.getId())
                .orderDate(orders.getOrderDate().toLocalDate().toString())
                .orderStatus(orders.getStatus().name())
                .contentId(orders.getContentId())
                .advertId(orders.getAdvertId())
                .productThumbnailUrl(productThumbnailUrl)
                .build();

        return response;
    }

    public CancelOrdersResponse cancelOrders(Long ordersId){
        Orders orders = ordersRepository.findById(ordersId).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        orders.cancelOrders();

        return CancelOrdersResponse.builder().result("success").build();
    }

}
