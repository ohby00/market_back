package com.osio.market.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {
    private Long productId;
    private String productName;
    private String productCategory;
    private Long productPrice;
    private String productImage;
}