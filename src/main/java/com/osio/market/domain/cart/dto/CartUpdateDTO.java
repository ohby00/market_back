package com.osio.market.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateDTO {
    private Long cartId;
    private Long cartProductId;
    private Long cartQuantity;
    private Long ProductId;
}
