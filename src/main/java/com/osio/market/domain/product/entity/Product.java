package com.osio.market.domain.product.entity;

import com.osio.market.domain.cart.entity.CartDetail;
import com.osio.market.domain.order.entity.OrderDetail;
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
    private Integer productStack;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetail;

    @OneToMany(mappedBy = "product")
    private List<CartDetail> cartDetail;
}
