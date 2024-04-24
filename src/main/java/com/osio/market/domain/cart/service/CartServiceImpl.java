package com.osio.market.domain.cart.service;


import com.osio.market.domain.cart.dto.CartListDTO;
import com.osio.market.domain.cart.entity.Cart;
import com.osio.market.domain.cart.entity.CartDetail;
import com.osio.market.domain.cart.repository.CartDetailRepository;
import com.osio.market.domain.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;


    @Override
    public List<CartListDTO> getCartList(Long userId) {
        // 유저 장바구니 조회
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new NoSuchElementException("Cart not found : " + userId);
        }

        // 장바구니 List를 DTO로 변환하여 리스트로 반환
        List<CartDetail> cartDetails = cartDetailRepository.findAllByCart(cart);
        return cartDetails.stream()
                .map(cartDetail -> CartListDTO.builder()
                        .productName(cartDetail.getProduct().getProductName())
                        .productPrice(cartDetail.getProduct().getProductPrice())
                        .image(cartDetail.getProduct().getProductImage())
                        .cartPrice(cartDetail.getCart().getCartPrice())
                        .cartStack(cartDetail.getCartStack())
                        .cartId(cartDetail.getCart().getCartId())
                        .build())
                .collect(Collectors.toList());
    }

//
//    @Override
//    public void addCart(CartAddDTO cartAddDTO) {
//
//    }


}
