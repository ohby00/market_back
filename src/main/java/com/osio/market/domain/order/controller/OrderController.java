package com.osio.market.domain.order.controller;

import com.osio.market.domain.order.dto.OrderProductQuantityDTO;
import com.osio.market.domain.order.dto.OrderProductsListDTO;
import com.osio.market.domain.order.dto.OrdersListDTO;
import com.osio.market.domain.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin()
@Slf4j
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    /* 주문 조회
         http://localhost:8080/order/list
    */
    @GetMapping("/list")
    public ResponseEntity<List<OrdersListDTO>> getOrdersList(Principal principal) {
        try{
            List<OrdersListDTO> orderList = orderServiceImpl.getOrdersList(principal);
            return ResponseEntity.ok(orderList);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 주문 번호 1의 상품 조회 (작동 안함)
    @GetMapping("/product/list/{orderId}")
    public ResponseEntity<List<OrderProductsListDTO>> getOrderProductsList(@PathVariable("orderId") Long orderId, Principal principal) {
        try{
            List<OrderProductsListDTO> orderList = orderServiceImpl.getOrderProductsList(orderId, principal);
            return ResponseEntity.ok(orderList);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 주문 추가 (장바구니 상품이 아닌 상품 직접 구매)
    /*
        http://localhost8080/order/add/1

        {
        "orderProductQuantity" : 2,
        }
    */
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addOrder(@RequestBody OrderProductQuantityDTO orderProductQuantity, @PathVariable("productId") Long productId, Principal principal) {
        try {
            String order = orderServiceImpl.addOrder(orderProductQuantity, productId, principal);
            return ResponseEntity.ok("주문이 생성되었습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 생성에 실패하였습니다.");
        }
    }

    // 주문 추가 (장바구니 상품이 아닌 상품 직접 구매)
    /*
        http://localhost8080/order/delete/1
    */
    @PostMapping("/delete/{orderId}")
    public ResponseEntity<String> canceledOrder(@PathVariable("orderId") Long orderId, Principal principal) {
            String order = orderServiceImpl.canceledOrder(orderId, principal);
            return ResponseEntity.ok(order);
    }
}
