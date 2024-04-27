package com.osio.market.domain.order.dto;

import com.osio.market.domain.order.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersListDTO {
    private Long orderId;
    private Long userId;
    private Timestamp orderDate;
    private Long orderTotalPrice;
    private Status status;
}
