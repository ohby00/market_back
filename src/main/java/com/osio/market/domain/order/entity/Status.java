package com.osio.market.domain.order.entity;

public enum Status {
    READY_TO_SHIPPING,  // 배송전
    SHIPPING,           // 배송중
    DELIVERED,          // 배송완료
    RETURNING,             // 반품
    REFUND,
    CANCELED            // 취소
}
