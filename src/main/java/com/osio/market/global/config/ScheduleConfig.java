package com.osio.market.global.config;

import com.osio.market.domain.order.entity.Status;
import com.osio.market.domain.order.service.OrderService;
import com.osio.market.domain.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class ScheduleConfig {
    private OrderServiceImpl orderService;

    @Autowired
    public ScheduleConfig(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Scheduled(fixedRate = 30000)
    public void scheduled() {
        orderService.updateOrderStatus();
        log.info("Order status updated");
    }
}
