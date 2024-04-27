package com.osio.market.domain.order.reposiroty;

import com.osio.market.domain.order.entity.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProducts, Long> {
//    List<OrderProducts> findByAllOrderProducts(Optional<Orders> orders);

//    List<OrderProducts> findByOrderId(Long orderId);
}
