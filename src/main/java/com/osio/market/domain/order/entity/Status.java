package com.osio.market.domain.order.entity;

public enum Status {
    READY_TO_SHIPPING,  // 배송전
    SHIPPING,           // 배송중
    DELIVERED,          // 배송완료
    REFUND,             // 반품
    CANCELED            // 취소
}
