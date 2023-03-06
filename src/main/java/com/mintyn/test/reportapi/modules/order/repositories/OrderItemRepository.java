package com.mintyn.test.reportapi.modules.order.repositories;

import com.mintyn.test.reportapi.modules.order.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}