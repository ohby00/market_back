package com.osio.market.domain.order.entity;

import com.osio.market.domain.product.entity.Product;
import com.osio.market.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderdetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    private Long orderStack;
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name="orderId")
    private Orders orders;
}
