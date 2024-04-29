package com.osio.market.domain.order.reposiroty;

import com.osio.market.domain.order.entity.OrderProducts;
import com.osio.market.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProducts, Long> {
//    List<OrderProducts> findByOrderId(Optional<User> orderId);
}
