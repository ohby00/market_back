package com.osio.market.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartListDTO {
    private Long cartId;
    private Long productId;
    private String productName;

    private Long productPrice;
    private String image;
    private Long cartPrice;
    private Long cartStack;
}
