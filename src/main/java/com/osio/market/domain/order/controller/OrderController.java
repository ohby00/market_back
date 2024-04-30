package com.osio.market.domain.order.controller;

import com.osio.market.domain.order.dto.OrderProductQuantityDTO;
import com.osio.market.domain.order.dto.OrdersListDTO;
import com.osio.market.domain.order.entity.Orders;
import com.osio.market.domain.order.reposiroty.OrderRepository;
import com.osio.market.domain.order.service.OrderServiceImpl;
import com.osio.market.domain.product.entity.Product;
import com.osio.market.domain.product.service.ProductService;
import com.osio.market.domain.user.entity.User;
import com.osio.market.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;
    private final ProductService productService;
    private final UserJpaRepository userJpaRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/list")
    public ResponseEntity<List<OrdersListDTO>> getOrdersList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            List<OrdersListDTO> orderList = orderServiceImpl.getOrdersList();
            return ResponseEntity.ok(orderList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
  
  
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addOrder(@RequestBody OrderProductQuantityDTO orderProductQuantity, @PathVariable("productId") Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            // 상품 정보 조회
            Optional<Product> productOptional = productService.findById(productId);
            if (productOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("상품을 찾을 수 없습니다.");
            }

            Product product = productOptional.get();

            // 주문하려는 상품의 재고 수량 확인
            int orderedQuantity = orderProductQuantity.getOrderProductQuantityDTO();
            int availableQuantity = product.getProductQuantity();

            if (orderedQuantity > availableQuantity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("상품의 재고가 부족합니다.");
            }

            // 주문 생성
            String order = orderServiceImpl.addOrder(orderProductQuantity, productId);

            // 상품의 재고에서 주문된 수량 차감
            int updatedQuantity = availableQuantity - orderedQuantity;
            product.setProductQuantity(updatedQuantity);
            productService.save(product);

            return ResponseEntity.ok("주문이 생성되었습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 생성에 실패하였습니다.");
        }
    }


    // 주문 취소 (DeleteMapping하면 주문이 사라지므로 PostMapping으로 상태만 변경)
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<String> canceledOrder(@PathVariable("orderId") Long orderId) {
            String order = orderServiceImpl.canceledOrder(orderId);
            return ResponseEntity.ok(order);

    }
}
