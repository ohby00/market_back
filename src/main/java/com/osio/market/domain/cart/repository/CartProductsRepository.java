package com.osio.market.domain.cart.repository;

import com.osio.market.domain.cart.entity.Cart;
import com.osio.market.domain.cart.entity.CartProducts;
import com.osio.market.domain.product.entity.Product;
import com.osio.market.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts, Long> {
//
//    CartProducts findByCartAndProduct(Cart cart , Product product);
//
    List<CartProducts> findAllByCart(Optional<Cart> cart);


}
