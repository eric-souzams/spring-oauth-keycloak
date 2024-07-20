package com.project.order_service.external.client;

import com.project.order_service.exception.CustomErrorException;
import com.project.order_service.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/v1/payments")
public interface PaymentService {

    @PostMapping
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request);

    default void fallback(Exception e) {
        throw new CustomErrorException("Payment service is not available", "UNAVAILABLE", 500);
    }

}
