package com.osio.market.domain.order.dto;

import com.osio.market.domain.order.entity.Status;
import com.osio.market.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddDTO {
    private User user;
    private Long orderid;
    private Status status;
    private Long orderTotalPrice;
    private Timestamp orderDate;
    private Long orderProductQuantity;
}
