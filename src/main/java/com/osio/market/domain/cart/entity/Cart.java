package com.osio.market.domain.cart.entity;

import com.osio.market.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    private Long cartId;
    private Long cartTotalPrice;

    @OneToMany(mappedBy = "cart")
    private List<CartProducts> cartProducts;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // userId와 동일한 값으로 cartId 매핑
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
