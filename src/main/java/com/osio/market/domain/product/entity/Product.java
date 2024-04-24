package com.osio.market.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String productDetail;

    @Column(nullable = false)
    private String productImage;

    @Column(nullable = false)
    private Integer productStack;
}
