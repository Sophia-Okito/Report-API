package com.mintyn.test.reportapi.modules.order.services;

import com.mintyn.test.reportapi.modules.order.repositories.OrderItemRepository;
import com.mintyn.test.reportapi.modules.order.repositories.OrderRepository;
import com.mintyn.test.reportapi.modules.product.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void createOrder() {
        //todo write coverage test
    }
}