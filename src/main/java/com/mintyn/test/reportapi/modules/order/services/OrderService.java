package com.mintyn.test.reportapi.modules.order.services;

import com.mintyn.test.reportapi.dtos.StandardResponse;
import com.mintyn.test.reportapi.modules.order.dtos.OrderDto;

import java.time.LocalDate;

public interface OrderService {
    void createOrder(OrderDto dto);
    StandardResponse getAllOrders(LocalDate start, LocalDate end);
}
