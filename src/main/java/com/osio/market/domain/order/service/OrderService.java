package com.osio.market.domain.order.service;

import com.osio.market.domain.order.dto.OrderProductQuantityDTO;
import com.osio.market.domain.order.dto.OrderProductsListDTO;
import com.osio.market.domain.order.dto.OrdersListDTO;
import com.osio.market.domain.order.entity.Status;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    List<OrderProductsListDTO> getOrderProductsList(Long orderId, Principal principal);

    String addOrder(OrderProductQuantityDTO orderProductQuantity, Long productId, Principal principal);

    String canceledOrder(Long orderId,Principal principal);

//    String refundOrder(Long orderId,Principal principal);

    List<OrdersListDTO> getOrdersList(Principal principal);

    @Transactional
    void updateOrderStatus();

//    @Transactional
//    void updateRefundOrder();
}
