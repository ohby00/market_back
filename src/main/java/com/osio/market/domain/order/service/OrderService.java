package com.osio.market.domain.order.service;

import com.osio.market.domain.order.dto.OrderProductQuantityDTO;
import com.osio.market.domain.order.dto.OrderProductsListDTO;
import com.osio.market.domain.order.dto.OrdersListDTO;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    List<OrderProductsListDTO> getOrderProductsList(Long orderId);

    String addOrder(OrderProductQuantityDTO orderProductQuantity, Long productId);

    String canceledOrder(Long orders);

    List<OrdersListDTO> getOrdersList();

    @Transactional
    void updateOrderStatus();
}
