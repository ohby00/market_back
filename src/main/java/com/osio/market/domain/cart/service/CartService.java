package com.osio.market.domain.cart.service;

import com.osio.market.domain.cart.dto.CartAddDTO;
import com.osio.market.domain.cart.dto.CartListDTO;
import com.osio.market.domain.cart.entity.CartProducts;

import java.security.Principal;
import java.util.List;

public interface CartService {
    // 사용자 장바구니 조회
    List<CartListDTO> getCartList(Long userId);

    // 장바구니 상품 추가
    String addCartProduct(Principal principal, CartAddDTO cartAddDTO);

    // 장바구니 수량 변경
    String updateCartProduct(Principal principal, CartAddDTO cartAddDTO);

    // 장바구니 상품 제거
    String deleteCartProduct(Principal principal, CartProducts cartProductId);
}
