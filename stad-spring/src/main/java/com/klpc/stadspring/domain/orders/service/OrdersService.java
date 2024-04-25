package com.klpc.stadspring.domain.orders.service;

import com.klpc.stadspring.domain.cart.controller.response.GetCartProductInfoResponse;
import com.klpc.stadspring.domain.delivery.entity.Delivery;
import com.klpc.stadspring.domain.delivery.repository.DeliveryRepository;
import com.klpc.stadspring.domain.orderProduct.entity.OrderProduct;
import com.klpc.stadspring.domain.orderProduct.repository.OrderProductRepository;
import com.klpc.stadspring.domain.orders.controller.request.AddOrdersRequest;
import com.klpc.stadspring.domain.orders.controller.response.AddOrdersResponse;
import com.klpc.stadspring.domain.orders.controller.response.GetOrdersListResponse;
import com.klpc.stadspring.domain.orders.entity.Orders;
import com.klpc.stadspring.domain.orders.repository.OrdersRepository;
import com.klpc.stadspring.domain.orders.service.command.request.AddOrderRequestCommand;
import com.klpc.stadspring.domain.orders.service.command.response.GetOrdersListResponseCommand;
import com.klpc.stadspring.domain.product.entity.Product;
import com.klpc.stadspring.domain.product.repository.ProductRepository;
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
    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrdersRepository ordersRepository;

    @Transactional(readOnly = false)
    public AddOrdersResponse addOrders(AddOrderRequestCommand command){
        User user = userRepository.findById(command.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        Product product = productRepository.findById(command.getProductId()).orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

        Orders orders = Orders.createToOrders(user, command.getContentId(), command.getAdvertId());
        ordersRepository.save(orders);

        OrderProduct orderProduct = OrderProduct.createToOrderProduct(command.getProductCnt());
        orderProduct.linkedOrders(orders);
        orderProduct.linkedProduct(product);
        orderProductRepository.save(orderProduct);

        Delivery delivery = Delivery.createToDelivery(
                command.getPhoneNumber(),
                command.getName(),
                command.getLocation(),
                command.getLocationDetail(),
                command.getLocationName(),
                command.getLocationNum()
        );
        delivery.linkedOrders(orders);
        deliveryRepository.save(delivery);

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
                productIdList.add(orderProduct.getProduct().getId());
            }
            List<String> productNameList = new ArrayList<>();
            for(OrderProduct orderProduct : orders.getOrderProducts()){
                productNameList.add(orderProduct.getProduct().getName());
            }
            List<String> productThumbnailUrl = new ArrayList<>();
            for(OrderProduct orderProduct : orders.getOrderProducts()){
                productNameList.add(orderProduct.getProduct().getThumbnail());
            }
            GetOrdersListResponseCommand command = GetOrdersListResponseCommand.builder()
                    .ordersId(orders.getId())
                    .orderDate(orders.getOrderDate().toLocalDate().toString())
                    .contentId(orders.getContentId())
                    .advertId(orders.getAdvertId())
                    .deliveryStatus(orders.getDelivery().getStatus().toString())
                    .productId(productIdList)
                    .productName(productNameList)
                    .productThumbnailUrl(productThumbnailUrl)
                    .build();
            response.add(command);
        }
        return GetOrdersListResponse.builder().data(response).build();
    }

}
