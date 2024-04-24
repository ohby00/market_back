package com.osio.market.domain.cart.entity;

import com.osio.market.domain.user.entity.User;
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
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private Long cartPrice;

    @OneToMany(mappedBy = "cart")
    private List<CartDetail> cartDetail;

    @OneToOne
    @JoinColumn(name = "userId", unique =true)
    private User user;
}
