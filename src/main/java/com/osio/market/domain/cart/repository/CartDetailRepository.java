package com.osio.market.domain.cart.repository;

import com.osio.market.domain.cart.entity.Cart;
import com.osio.market.domain.cart.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    List<CartDetail> findAllByCart(Cart cart);
}
