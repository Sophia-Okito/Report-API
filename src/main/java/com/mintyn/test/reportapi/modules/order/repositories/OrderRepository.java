package com.mintyn.test.reportapi.modules.order.repositories;

import com.mintyn.test.reportapi.modules.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}