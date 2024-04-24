package com.osio.market.domain.cart.controller;

import com.osio.market.domain.cart.dto.CartListDTO;
import com.osio.market.domain.cart.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
