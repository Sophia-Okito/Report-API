package com.mintyn.test.reportapi.modules.order.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {

    private String customerName;

    private String customerPhoneNumber;

    private Set<OrderItemDto> items;

}
