package com.osio.market.domain.cart.controller;


import com.osio.market.domain.cart.dto.CartAddDTO;
import com.osio.market.domain.cart.dto.CartListDTO;
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

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<CartListDTO>> getCartList(@PathVariable("userId") Long userId) {
        try {
            List<CartListDTO> cartList = cartServiceImpl.getCartList(userId);
            return ResponseEntity.ok(cartList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

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

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteProductCart(@RequestBody CartProducts cartProducts, Principal principal) {
        try{
            String result = cartServiceImpl.deleteCartProduct(principal, cartProducts);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("제거할 상품이 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delet cart");
        }
    }
}
