package com.project.order_service.service.impl;

import com.project.order_service.entity.Order;
import com.project.order_service.external.client.PaymentService;
import com.project.order_service.external.client.ProductService;
import com.project.order_service.external.request.OrderRequest;
import com.project.order_service.external.request.PaymentRequest;
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

    @Autowired
    private PaymentService paymentService;

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

        log.info("Calling payment service to complete the payment...");

        String orderStatus = null;
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getOrderId())
                .paymentMode(request.getPaymentMode())
                .totalAmount(request.getTotalAmount())
                .build();

        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done with successfully...");
            log.info("Changing order {} status to PLACED...", order.getOrderId());
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("An error occurred in payment service...");
            log.error("Changing order {} status to PAYMENT_FAILED...", order.getOrderId());
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order placed with successfully...");

        return order.getOrderId();
    }
}
