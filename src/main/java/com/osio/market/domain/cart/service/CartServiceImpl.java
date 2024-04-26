package com.osio.market.domain.cart.service;

import com.osio.market.domain.cart.dto.CartAddDTO;
import com.osio.market.domain.cart.dto.CartListDTO;
import com.osio.market.domain.cart.dto.CartUpdateDTO;
import com.osio.market.domain.cart.entity.Cart;
import com.osio.market.domain.cart.entity.CartProducts;
import com.osio.market.domain.cart.repository.CartProductsRepository;
import com.osio.market.domain.cart.repository.CartRepository;
import com.osio.market.domain.product.entity.Product;
import com.osio.market.domain.product.repository.ProductRepository;
import com.osio.market.domain.user.entity.User;
import com.osio.market.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartProductsRepository cartProductsRepository;
    private final ProductRepository productRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public List<CartListDTO> getCartList(Principal principal) {
        Optional<User> userId = userJpaRepository.findByEmail(principal.getName());

        // 유저 장바구니 조회
        Optional<Cart> cart = cartRepository.findById(userId.get().getId());

        // 장바구니 List를 DTO로 변환하여 리스트로 반환
        List<CartProducts> cartProducts = cartProductsRepository.findAllByCart(cart);
        return cartProducts.stream()
                .map(cartProduct -> CartListDTO.builder()
                        .cartProductId(cartProduct.getCartProductId())
                        .productId(cartProduct.getProduct().getProductId())
                        .productName(cartProduct.getProduct().getProductName())
                        .cartProductPrice(cartProduct.getCartProductPrice())
                        .image(cartProduct.getProduct().getProductImage())
                        .cartProductPrice(cartProduct.getCartProductPrice())
                        .cartProductQuantity(cartProduct.getCartQuantity())
                        .cartId(cartProduct.getCart().getCartId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String addCartProduct(Principal principal, CartAddDTO cartAddDTO) {
        Optional<User> userId = userJpaRepository.findByEmail(principal.getName());

        // Product 조회
        Product product = productRepository.findById(cartAddDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Optional 에서 값을 받아오려면 get().getId()로 받아와야 함
        Cart cart = cartRepository.findById(userId.get().getId()).get();

        // cartId : 1 에 Product가 존재하는지 판별
        CartProducts existCartProduct = cartProductsRepository.findByCartAndProduct(cart, product);

        if (existCartProduct != null) {
            return "이미 장바구니에 있는 상품입니다.";
        }

        // CartProduct 생성 및 설정
        CartProducts cartProducts = new CartProducts();
        cartProducts.setCart(cart);
        cartProducts.setProduct(product);
        cartProducts.setCartQuantity(1L); // 장바구니 스택 설정
        cartProducts.setCartProductPrice(product.getProductPrice());

        // CartDetail 저장
        cartProductsRepository.save(cartProducts);
        return "장바구니 추가 완료";
    }

    @Override
    public String updateCartProduct(Principal principal, CartUpdateDTO cartUpdateDTO) {
        Optional<User> userId = userJpaRepository.findByEmail(principal.getName());
        Cart cart = cartRepository.findById(userId.get().getId()).get();

        Optional<Product> product = productRepository.findById(cartUpdateDTO.getProductId());

        CartProducts cartProduct = cartProductsRepository.findByCartProductId(cartUpdateDTO.getCartProductId());

        Long price = cartProduct.getCartQuantity() * product.get().getProductPrice();
        cartProduct.setCartQuantity(cartUpdateDTO.getCartQuantity());
        cartProduct.setCartProductPrice(price);
        cartProductsRepository.save(cartProduct);

        return "수정 완료";
    }

    @Override
    public String deleteCartProduct(Principal principal, CartProducts cartProductId) {
        cartProductsRepository.delete(cartProductId);

        return "상품 제거 완료";
    }
}