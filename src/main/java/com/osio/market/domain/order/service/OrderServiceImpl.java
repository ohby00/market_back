package com.osio.market.domain.order.service;

import com.osio.market.domain.order.dto.OrderProductQuantityDTO;
import com.osio.market.domain.order.dto.OrderProductsListDTO;
import com.osio.market.domain.order.dto.OrdersListDTO;
import com.osio.market.domain.order.entity.OrderProducts;
import com.osio.market.domain.order.entity.Status;
import com.osio.market.domain.order.entity.Orders;
import com.osio.market.domain.order.reposiroty.OrderProductRepository;
import com.osio.market.domain.order.reposiroty.OrderRepository;
import com.osio.market.domain.product.entity.Product;
import com.osio.market.domain.product.repository.ProductRepository;
import com.osio.market.domain.user.entity.User;
import com.osio.market.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserJpaRepository userJpaRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    // 리팩토링 완료
    private User findUserByEmail(Principal principal) {
        String userEmail = principal.getName();
        return userJpaRepository.findByEmail(userEmail).orElseThrow(()
                -> new UsernameNotFoundException("User not found with email: " + userEmail));
    }

    // 주문 조회
    @Override
    public List<OrdersListDTO> getOrdersList(Principal principal) {
        Optional<User> userOptional = Optional.ofNullable(findUserByEmail(principal));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getOrders().stream()
                    .map(orders -> OrdersListDTO.builder()
                            .orderId(orders.getOrderId())
                            .userId(orders.getUser().getId())
                            .orderDate(orders.getOrderDate())
                            .orderTotalPrice(orders.getOrderTotalPrice())
                            .status(orders.getStatus())
                            .build())
                    .collect(Collectors.toList());
        } else {
            // 사용자를 찾을 수 없는 경우에 대한 처리
            // 예외를 throw하거나 빈 리스트를 반환할 수 있음
            return Collections.emptyList();
        }
    }




    // 주문 번호 1의 상품 조회 (작동 안함)
    @Override
    public List<OrderProductsListDTO> getOrderProductsList(Long orderId, Principal principal) {
        User user = findUserByEmail(principal);
        Optional<Orders> ordersOptional = orderRepository.findById(orderId);

        if (ordersOptional.isPresent()) {
            Orders orders = ordersOptional.get();
            return orders.getOrderProducts().stream()
                    .map(orderProduct -> OrderProductsListDTO.builder()
                            .orderProductId(orderProduct.getOrderProductsId())
                            .orderProductQuantity(orderProduct.getOrderProductQuantity())
                            .orderProductPrice(orderProduct.getOrderProductPrice())
                            .build())
                    .collect(Collectors.toList());
        } else {
            // 주어진 orderId에 해당하는 주문이 존재하지 않음
            // 예외 처리 또는 다른 작업 수행
            return Collections.emptyList(); // 또는 예외를 throw할 수 있음
        }
    }


    // 주문 추가 (장바구니 상품이 아닌 상품 직접 구매)
    @Override
    @Transactional
    public String addOrder(OrderProductQuantityDTO orderProductQuantity, Long productId, Principal principal) {
        User user = findUserByEmail(principal);

        // 주문 상품을 DB에 저장
        Product product = productRepository.findById(productId).get();
        OrderProducts orderProduct = OrderProducts.builder()
                .product(product)
                .orderProductQuantity(orderProductQuantity.getOrderProductQuantityDTO())
                .orderProductPrice(product.getProductPrice() * orderProductQuantity.getOrderProductQuantityDTO())
                .build();
        orderProductRepository.save(orderProduct);

        Timestamp orderDate = new Timestamp(System.currentTimeMillis());

        // 주문을 DB에 저장
        Orders order = Orders.builder()
                .user(user)
                .status(Status.READY_TO_SHIPPING)
                .orderTotalPrice(productRepository.findById(productId).get().getProductPrice() * orderProductQuantity.getOrderProductQuantityDTO())
                .orderDate(orderDate)
                .build();
        orderRepository.save(order);

        // 주문 정보의 ID를 얻어온 후에 해당 ID로 주문 상품 정보에 연결
        orderProduct.setOrder(order);
        orderProductRepository.save(orderProduct);

        return "주문 완료";
    }


    // 주문 상태 변경
    @Override
    @Transactional
    public void updateOrderStatus() {
        List<Orders> orders = orderRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Orders order : orders) {
            LocalDateTime orderDate = order.getOrderDate().toLocalDateTime();
            long days = java.time.Duration.between(orderDate, now).toDays();
            if (order.getStatus() != Status.CANCELED && order.getStatus() != Status.REFUND) {
                if (days == 1) {
                    order.updateOrderStatus(Status.SHIPPING);
                } else if (days >= 2) {
                    order.updateOrderStatus(Status.DELIVERED);
                }
            }
        }
    }

    @Override
    public String canceledOrder(Long orderId, Principal principal) {
        User user = findUserByEmail(principal);
        Orders order = orderRepository.findById(orderId).get();
//        if (orderId.equals(order.getOrderId())) {
        if (order.getStatus().equals(Status.READY_TO_SHIPPING)) {
            order.updateOrderStatus(Status.CANCELED);
            return "주문 취소 완료";
        } else {
            return "주문 취소 불가";
        }
//        } return "주문 내역이 없습니다.";
    }
}
