package com.osio.market.domain.order.dto;

import com.osio.market.domain.order.entity.Orders;
import com.osio.market.domain.order.entity.Status;
import com.osio.market.domain.product.entity.Product;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsListDTO {
    private Orders orders;
    private Long orderProductId;
    private Product product;
    private Long orderQuantity;
    private Long orderProductQuantity;
    private Long orderProductPrice;
}
