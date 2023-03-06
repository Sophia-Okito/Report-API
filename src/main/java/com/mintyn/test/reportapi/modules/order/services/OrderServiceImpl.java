package com.mintyn.test.reportapi.modules.order.services;

import com.mintyn.test.reportapi.dtos.StandardResponse;
import com.mintyn.test.reportapi.enums.Status;
import com.mintyn.test.reportapi.modules.order.dtos.OrderDto;
import com.mintyn.test.reportapi.modules.order.dtos.OrderItemDto;
import com.mintyn.test.reportapi.modules.order.dtos.ReportDTO;
import com.mintyn.test.reportapi.modules.order.models.Order;
import com.mintyn.test.reportapi.modules.order.models.OrderItem;
import com.mintyn.test.reportapi.modules.order.repositories.OrderItemRepository;
import com.mintyn.test.reportapi.modules.order.repositories.OrderRepository;
import com.mintyn.test.reportapi.modules.product.models.Product;
import com.mintyn.test.reportapi.modules.product.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createOrder(OrderDto dto) {

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setCustomerName(dto.getCustomerName());
        order.setCustomerPhoneNumber(dto.getCustomerPhoneNumber());
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        saveOrderItems(dto.getItems(), order);

        log.info("order created successfully");

    }

    @Override
    public StandardResponse getAllOrders(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = Objects.nonNull(startDate) ? LocalDateTime.of(startDate, LocalTime.MIN) : null;
        LocalDateTime end = Objects.nonNull(endDate) ? LocalDateTime.of(endDate, LocalTime.MAX) : null;

        List<Order> orders;
        if (Objects.isNull(start) && Objects.isNull(end)) {
            orders = orderRepository.findAll();
        }else {
            orders = getOrdersUsingEntityManager(start, end);
        }

        if (orders.isEmpty()) {
            return new StandardResponse(Status.NO_CONTENT);
        }

        return new StandardResponse(Status.SUCCESS, new ReportDTO(orders));
    }


    private void saveOrderItems(Set<OrderItemDto> items, Order order) {
        for (OrderItemDto item : items) {
            Optional<Product> optionalProduct = productRepository.findByProductId(item.getId());
            if (optionalProduct.isEmpty()) {
                continue;
            }

            Product product = optionalProduct.get();
            if (product.getQuantity() < item.getQuantity()) {
                continue;
            }


            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setCreatedAt(LocalDateTime.now());
            orderItem.setUnitPrice(product.getUnitPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(product.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
            orderItemRepository.save(orderItem);

            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
        }


    }

    private List<Order> getOrdersUsingEntityManager(LocalDateTime start, LocalDateTime end) {
        List<Order> orders;
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Order> cq = qb.createQuery(Order.class);

        Root<Order> root = cq.from(Order.class);

        List<Predicate> predicates = getPredicates(start, end, qb, root);

        cq.select(root).where(predicates.toArray(new Predicate[]{}));

        orders = entityManager.createQuery(cq).getResultList();
        return orders;
    }


    private List<Predicate> getPredicates(LocalDateTime start, LocalDateTime end, CriteriaBuilder qb, Root<Order> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(start)) {
            predicates.add(qb.greaterThanOrEqualTo(root.get("createdAt"), start));
        }

        if (Objects.nonNull(end)) {
            predicates.add(qb.lessThan(root.get("createdAt"), end));
        }

        return predicates;
    }

}
