package com.osio.market.domain.cart.service;



import com.osio.market.domain.cart.dto.CartAddDTO;
import com.osio.market.domain.cart.dto.CartListDTO;
import com.osio.market.domain.cart.entity.Cart;
import com.osio.market.domain.cart.entity.CartProducts;
import com.osio.market.domain.cart.repository.CartProductsRepository;
import com.osio.market.domain.cart.repository.CartRepository;
import com.osio.market.domain.product.repository.ProductRepository;
import com.osio.market.domain.user.entity.User;
import com.osio.market.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartProductsRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final UserJpaRepository userJpaRepository;
    private final CartProductsRepository cartProductsRepository;

    @Override
    public List<CartListDTO> getCartList(Long userId) {
        // 유저 장바구니 조회
        Optional<Cart> cart = cartRepository.findById(userId);

        if (cart == null) {
            throw new NoSuchElementException("Cart not found : " + userId);
        }

        // 장바구니 List를 DTO로 변환하여 리스트로 반환
        List<CartProducts> cartDetails = cartProductsRepository.findAllByCart(cart);
        return cartDetails.stream()
                .map(cartDetail -> CartListDTO.builder()
                        .productName(cartDetail.getProduct().getProductName())
                        .cartProductPrice(cartDetail.getCartProductPrice())
                        .image(cartDetail.getProduct().getProductImage())
                        .cartTotalPrice(cartDetail.getCart().getCartTotalPrice())
                        .cartProductQuantity(cartDetail.getCartQuantity())
                        .cartId(cartDetail.getCart().getCartId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void addCartProduct(Principal principal, CartAddDTO cartAddDTO) {
        // Optional 에서 값을 받아오려면 get().getId()로 받아와야 함
        Optional<User> userId = userJpaRepository.findByEmail(principal.getName());

        // Cart 저장
//        cartRepository.save(cart);

//        // Product 조회
//        Product product = productRepository.findById(cartAddDTO.getProductId())
//                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//
//        CartProducts existCartDetail = cartDetailRepository.findByCartAndProduct(cart.orElse(null), product);
//
//        if (existCartDetail != null) {
//            existCartDetail.setCartQuantity(existCartDetail.getCartQuantity() + 1);
//            existCartDetail.setCartProductPrice(existCartDetail.getCartProductPrice()) + product.getProductPrice());
//            cartRepository.update(existCartDetail);
//              이미 장바구니에 있습니다  ~ ! 좋습니다 !
//        }

//        // CartProduct 생성 및 설정
//        CartProducts cartProducts = new CartProducts();
//        cartProducts.setCart(cart);
//        cartProducts.setProduct(product);
//        cartProducts.setCartQuantity(1L); // 장바구니 스택 설정
//        cartProducts.setCartProductPrice(product.getProductPrice());
//
//        // CartDetail 저장
//        cartDetailRepository.save(cartProducts);
    }

}