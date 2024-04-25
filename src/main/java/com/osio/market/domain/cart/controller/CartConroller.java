package com.osio.market.domain.cart.controller;


import com.osio.market.domain.cart.dto.CartAddDTO;
import com.osio.market.domain.cart.dto.CartListDTO;
import com.osio.market.domain.cart.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartConroller {

    private final CartServiceImpl cartServiceImpl;

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<CartListDTO>> getcartList(@PathVariable("userId") Long userId) {
        List<CartListDTO> cartList = cartServiceImpl.getCartList(userId);
        return ResponseEntity.ok(cartList);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCart(@RequestBody CartAddDTO cartAddDTO, Principal principal) {
        try {
            cartServiceImpl.addCartProduct(principal, cartAddDTO);
            return ResponseEntity.ok("Added cart successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save cart");
        }
    }
}
