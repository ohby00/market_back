package com.osio.market.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAddDTO {
    private Long productId;
    private Long cartId;
    private String productName;
    private String cartDetailId;
}