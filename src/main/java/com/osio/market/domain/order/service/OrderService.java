package com.osio.market.domain.order.service;

import com.osio.market.domain.order.dto.OrderProductQuantityDTO;
import com.osio.market.domain.order.dto.OrderProductsListDTO;
import com.osio.market.domain.order.dto.OrdersListDTO;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    List<OrderProductsListDTO> getOrderProductsList(Long orderId, Principal principal);

    String addOrder(OrderProductQuantityDTO orderProductQuantity, Long productId, Principal principal);

    String canceledOrder(Long orders,Principal principal);

    List<OrdersListDTO> getOrdersList(Principal principal);

    @Transactional
    void updateOrderStatus();
}
