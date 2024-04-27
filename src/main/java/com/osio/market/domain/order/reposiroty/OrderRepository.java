package com.osio.market.domain.order.reposiroty;


import com.osio.market.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
//    List<OrdersListDTO> findByAllOrders(Optional<Orders> orders);

}
