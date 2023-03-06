package com.mintyn.test.reportapi.modules.messagebroker.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mintyn.test.reportapi.modules.order.dtos.OrderDto;
import com.mintyn.test.reportapi.modules.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class KafkaConsumer implements MessageConsumer{

    private final OrderService orderService;

    @SneakyThrows
    @KafkaListener(topics = "${kafka.order.topic}", groupId = "default-group")
    @Override
    public void consume(String message) {

        OrderDto dto = new ObjectMapper().readValue(message, OrderDto.class);
        if (Objects.isNull(dto)) {
            return;
        }

        orderService.createOrder(dto);

    }
}
