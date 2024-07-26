package com.project.order_service.service.impl;

import com.project.order_service.entity.Order;
import com.project.order_service.enums.PaymentMode;
import com.project.order_service.exception.CustomErrorException;
import com.project.order_service.external.client.PaymentService;
import com.project.order_service.external.client.ProductService;
import com.project.order_service.external.request.PaymentRequest;
import com.project.order_service.model.OrderRequest;
import com.project.order_service.model.OrderResponse;
import com.project.order_service.model.ProductDetailsResponse;
import com.project.order_service.model.TransactionDetailsResponse;
import com.project.order_service.repository.OrderRepository;
import com.project.order_service.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @DisplayName("Get Order with success")
    @Test
    void testWhenGetOrderSuccess() {
        //mock
        Order order = getOrderMock();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(restTemplate.getForObject("http://PRODUCT-SERVICE/v1/products/" + order.getProductId(), ProductDetailsResponse.class)).thenReturn(getProductResponseMock());
        when(restTemplate.getForObject("http://PAYMENT-SERVICE/v1/payments/orders/" + order.getOrderId(), TransactionDetailsResponse.class)).thenReturn(getPaymentResponseMock());

        //ack
        OrderResponse orderResponse = orderService.getOrderDetails(1);

        //verify
        verify(orderRepository, times(1)).findById(anyLong());
        verify(restTemplate, times(1)).getForObject("http://PRODUCT-SERVICE/v1/products/" + order.getProductId(), ProductDetailsResponse.class);
        verify(restTemplate, times(1)).getForObject("http://PAYMENT-SERVICE/v1/payments/orders/" + order.getOrderId(), TransactionDetailsResponse.class);

        //check
        assertNotNull(orderResponse);
        assertEquals(order.getOrderId(), orderResponse.getOrderId());
    }

    @DisplayName("Get Order with failure")
    @Test
    void testWhenGetOrderReturnNotFound() {
        //mock
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        //ack & check
        CustomErrorException exception = assertThrows(CustomErrorException.class, () -> orderService.getOrderDetails(1));

        assertEquals("NOT_FOUND", exception.getErrorCode());
        assertEquals(404, exception.getStatus());

        //verify
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @DisplayName("Place Order with success")
    @Test
    void testWhenPlaceOrderSuccess() {
        //mock
        Order order = getOrderMock();
        OrderRequest orderRequest = getOrderRequestMock();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productService.reduceQuantity(anyLong(), anyLong())).thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        when(paymentService.doPayment(any(PaymentRequest.class))).thenReturn(new ResponseEntity<Long>(1L, HttpStatus.OK));

        //ack
        long orderId = orderService.placeOrder(orderRequest);

        //verify
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(productService, times(1)).reduceQuantity(anyLong(), anyLong());
        verify(paymentService, times(1)).doPayment(any(PaymentRequest.class));

        //check
        assertEquals(order.getOrderId(), orderId);
    }

    @DisplayName("Payment in place Order with failure")
    @Test
    void testWhenPaymentPlaceOrderReturnFails() {
        //mock
        Order order = getOrderMock();
        OrderRequest orderRequest = getOrderRequestMock();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productService.reduceQuantity(anyLong(), anyLong())).thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        when(paymentService.doPayment(any(PaymentRequest.class))).thenThrow(new RuntimeException());

        //ack
        long orderId = orderService.placeOrder(orderRequest);

        //check
        assertEquals(order.getOrderId(), orderId);
    }

    private OrderRequest getOrderRequestMock() {
        return OrderRequest.builder()
                .productId(1)
                .quantity(10)
                .paymentMode(PaymentMode.CASH)
                .totalAmount(100.00)
                .build();
    }

    private TransactionDetailsResponse getPaymentResponseMock() {
        return TransactionDetailsResponse.builder()
                .paymentId(1)
                .paymentDate(Instant.now())
                .paymentMode(PaymentMode.CASH)
                .paymentStatus("ACCEPTED")
                .totalAmount(200.00)
                .orderId(1)
                .build();
    }

    private ProductDetailsResponse getProductResponseMock() {
        return ProductDetailsResponse.builder()
                .productId(2)
                .productName("Book")
                .price(100.00)
                .quantity(20)
                .build();
    }

    private Order getOrderMock() {
        return Order.builder()
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .productId(2)
                .orderId(1)
                .totalAmount(200.00)
                .quantity(200)
                .build();
    }

}