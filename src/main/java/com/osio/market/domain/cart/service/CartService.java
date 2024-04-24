package com.osio.market.domain.cart.service;

import com.osio.market.domain.cart.dto.CartAddDTO;
import com.osio.market.domain.cart.dto.CartListDTO;
import java.util.List;

public interface CartService {
    // 사용자 장바구니 조회
    List<CartListDTO> getCartList(Long cartId);

    // 장바구니 상품 추가
//    void addCart(CartAddDTO cartAddDTO);
}
