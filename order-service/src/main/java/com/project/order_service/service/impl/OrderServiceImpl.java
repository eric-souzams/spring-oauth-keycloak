package com.project.order_service.service.impl;

import com.project.order_service.entity.Order;
import com.project.order_service.external.client.ProductService;
import com.project.order_service.model.OrderRequest;
import com.project.order_service.repository.OrderRepository;
import com.project.order_service.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    @Override
    public long placeOrder(OrderRequest request) {
        log.info("Placing order...");

        productService.reduceQuantity(request.getProductId(), request.getQuantity());

        log.info("Creating order...");
        Order order = Order.builder()
                .totalAmount(request.getTotalAmount())
                .orderStatus("CREATED")
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .orderDate(Instant.now())
                .build();

        orderRepository.save(order);

        // call payment service to check out - success ou cancelled

        log.info("Order placed...");

        return order.getOrderId();
    }
}
