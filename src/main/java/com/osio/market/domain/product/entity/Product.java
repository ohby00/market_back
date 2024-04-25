package com.osio.market.domain.product.entity;

import com.osio.market.domain.cart.entity.CartProducts;
import com.osio.market.domain.order.entity.OrderProducts;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productCategory;

    @Column(nullable = false)
    private Long productPrice;

    @Column(nullable = false, columnDefinition ="TEXT")
    private String productDetail;

    @Column(nullable = false)
    private String productImage;

    @Column(nullable = false)
    private Integer productQuantity;

    @OneToMany(mappedBy = "product")
    private List<OrderProducts> orderProducts;

    @OneToMany(mappedBy = "product")
    private List<CartProducts> cartProducts;
}
