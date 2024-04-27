package com.osio.market.domain.cart.controller;

import com.osio.market.domain.cart.dto.CartAddDTO;
import com.osio.market.domain.cart.dto.CartListDTO;
import com.osio.market.domain.cart.dto.CartUpdateDTO;
import com.osio.market.domain.cart.entity.CartProducts;
import com.osio.market.domain.cart.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartConroller {

    private final CartServiceImpl cartServiceImpl;

    /* 장바구니 상품 조회
         http://localhost8080/cart/list
     */
    @GetMapping("/list")
    public ResponseEntity<List<CartListDTO>> getCartList(Principal principal) {
        try {
            List<CartListDTO> cartList = cartServiceImpl.getCartList(principal);
            return ResponseEntity.ok(cartList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    /* 장바구니 상품 추가
         http://localhost8080/cart/add

         {
        "productId": 1
        }
    */
    @PostMapping("/add")
    public ResponseEntity<Object> addProductCart(@RequestBody CartAddDTO cartAddDTO, Principal principal) {
        try {
            String result = cartServiceImpl.addCartProduct(principal, cartAddDTO);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save cart");
        }
    }

    /* 장바구니 수량 수정
     http://localhost8080/cart/update

     {
    "productId" : 1,
    "cartQuantity" : 1,
    "cartId" : 1,
    "cartProductId" : 1
    }
    */
    @PatchMapping("/update")
    public ResponseEntity<Object> updateProductCart(@RequestBody CartUpdateDTO cartUpdateDTO, Principal principal) {
        String result = cartServiceImpl.updateCartProduct(principal, cartUpdateDTO);
        return ResponseEntity.ok(result);
    }


    /* 장바구니 상품 삭제
     http://localhost8080/cart/delete

     {
    "cartProductId" : 1
    }
    */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProductCart(@RequestBody CartProducts cartProducts, Principal principal) {
        try {
            String cartProduct = cartServiceImpl.deleteCartProduct(cartProducts, principal);
            return ResponseEntity.ok(cartProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete cart");
        }
    }
}
