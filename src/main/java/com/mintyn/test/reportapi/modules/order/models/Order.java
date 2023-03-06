package com.mintyn.test.reportapi.modules.order.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String orderId;

    private String customerName;

    private String customerPhoneNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private Set<OrderItem> items;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonProperty("totalPrice")
    public BigDecimal totalPrice() {
        if (items == null) {
            return BigDecimal.ZERO;
        }

        return items.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

}
