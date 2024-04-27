package com.osio.market.global.config;

import com.osio.market.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleConfig {
    private OrderService orderService;

    @Scheduled(fixedRate = 10000)
    public void scheduled() {
        orderService.updateOrderStatus();
    }
}
