package com.project.order_service.service.impl;

import com.project.order_service.entity.Order;
import com.project.order_service.exception.CustomErrorException;
import com.project.order_service.external.client.PaymentService;
import com.project.order_service.external.client.ProductService;
import com.project.order_service.model.OrderRequest;
import com.project.order_service.external.request.PaymentRequest;
import com.project.order_service.model.OrderResponse;
import com.project.order_service.model.ProductDetailsResponse;
import com.project.order_service.model.TransactionDetailsResponse;
import com.project.order_service.repository.OrderRepository;
import com.project.order_service.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

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

    @Transactional(readOnly = true)
    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Getting order details for order {}...", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomErrorException("Order not found fot the order id " + orderId, "NOT_FOUND", 404));

        log.info("Invoking product service to fetch the product {}...", order.getProductId());

        ProductDetailsResponse productDetailsResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/v1/products/" + order.getProductId(),
                ProductDetailsResponse.class
        );

        log.info("Invoking payment service to fetch the transaction details...");

        TransactionDetailsResponse transactionDetailsResponse = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/v1/payments/orders/" + orderId,
                TransactionDetailsResponse.class
        );

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .totalAmount(order.getTotalAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetailsResponse)
                .transactionDetails(transactionDetailsResponse)
                .build();
    }
}
