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
import com.osio.market.domain.product.service.ProductService;
import com.osio.market.domain.user.entity.User;
import com.osio.market.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Duration;
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
    private final ProductService productService;

    // 사용자 인증 정보를 가져오는 메서드
    private User findUserByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return userJpaRepository.findByEmail(userEmail).orElseThrow(()
                -> new UsernameNotFoundException("User not found with email: " + userEmail));
    }

    // 주문 조회
    @Override
    public List<OrdersListDTO> getOrdersList() {
        User user = findUserByEmail();
        return user.getOrders().stream()
                .map(orders -> OrdersListDTO.builder()
                        .orderId(orders.getOrderId())
                        .userId(orders.getUser().getId())
                        .orderDate(orders.getOrderDate())
                        .orderTotalPrice(orders.getOrderTotalPrice())
                        .status(orders.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    // 주문 번호 1의 상품 조회
    @Override
    public List<OrderProductsListDTO> getOrderProductsList(Long orderId) {
        User user = findUserByEmail();
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
            return Collections.emptyList(); // 또는 예외를 throw
        }
    }

    // 주문 추가 (장바구니 상품이 아닌 상품 직접 구매)
    @Override
    @Transactional
    public String addOrder(OrderProductQuantityDTO orderProductQuantity, Long productId) {
        User user = findUserByEmail();

        // 주문 상품을 DB에 저장
        Product product = productRepository.findById(productId).orElse(null); // 상품이 없을 경우 처리
        if (product == null) {
            return "상품을 찾을 수 없습니다.";
        }

        // 주문 상품 생성
        OrderProducts orderProduct = OrderProducts.builder()
                .product(product)
                .orderProductQuantity((long) orderProductQuantity.getOrderProductQuantityDTO())
                .orderProductPrice(product.getProductPrice() * orderProductQuantity.getOrderProductQuantityDTO())
                .build();

        // 현재 날짜와 시간을 가져옵니다.
        LocalDateTime now = LocalDateTime.now();

        // LocalDateTime 객체를 Timestamp 객체로 변환합니다.
        Timestamp orderDate = Timestamp.valueOf(now);
        log.info("orderDate={}", orderDate);

        Orders order = Orders.builder()
                .user(user)
                .status(Status.READY_TO_SHIPPING)
                .orderTotalPrice(orderProduct.getOrderProductPrice()) // 주문 상품 가격으로 설정
                .orderDate(orderDate)
                .build();

        // 주문 저장
        orderRepository.save(order);

        // 주문 상품과 주문의 관계 설정 후 저장
        orderProduct.setOrder(order);
        orderProductRepository.save(orderProduct);

        return "주문 완료";
    }


    // 주문 상태 변경
    @Override
    @Transactional
    public void updateOrderStatus() {
        List<Orders> orders = orderRepository.findAll();
        LocalDateTime now = LocalDateTime.now(); // 현재 시간

        for (Orders order : orders) {
            LocalDateTime orderDate = order.getOrderDate().toLocalDateTime();

            // 날짜 차이
            long days = Duration.between(orderDate, now).toDays();
            if (order.getStatus() != Status.CANCELED && order.getStatus() != Status.REFUND) {
                if (days == 1) { // 1일
                    order.updateStatus(Status.SHIPPING);
                } else if (days == 2) { // 2일
                    order.updateStatus(Status.DELIVERED);
                }
            }
        }
    }


    @Override
    public String canceledOrder(Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userJpaRepository.findByEmail(userEmail).orElseThrow(()
                -> new UsernameNotFoundException("User not found with email: " + userEmail));

        Optional<Orders> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Orders order = orderOptional.get();
            if (order.getUser().equals(user) && order.getStatus().equals(Status.READY_TO_SHIPPING)) {
                order.updateStatus(Status.CANCELED);
                return "주문 취소 완료";
            } else {
                return "주문 취소 불가";
            }
        } else {
            return "주문 내역이 없습니다.";
        }
    }

}




