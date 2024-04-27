package com.osio.market.domain.order.entity;

import com.osio.market.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderProducts")
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductsId;
    private Long orderProductQuantity;
    private Long orderProductPrice;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name="orderId")
    private Orders orders;
}
