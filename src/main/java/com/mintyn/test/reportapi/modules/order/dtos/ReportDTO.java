package com.mintyn.test.reportapi.modules.order.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mintyn.test.reportapi.modules.order.models.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private List<Order> orders = new ArrayList<>();


    @JsonProperty("totalOrderPrice")
    public BigDecimal totalOrderPrice() {
        return orders.stream().map(Order::totalPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @JsonProperty("totalOrder")
    public int totalOrder() {
        return orders.size();
    }
}
