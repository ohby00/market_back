package com.osio.market.domain.cart.entity;

import com.osio.market.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartProducts")
public class CartProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartProductId;
    private Long cartQuantity;
    private Long cartProductPrice;

    @ManyToOne
    @JoinColumn(name="cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;
}