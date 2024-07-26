package com.project.order_service.external.client;

import com.project.order_service.exception.CustomErrorException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE/v1/products")
public interface ProductService {

    @PutMapping("/reduce-quantity/{productId}")
    ResponseEntity<Void> reduceQuantity(@PathVariable("productId") long productId, @RequestParam long quantity);

    default ResponseEntity<Void> fallback(Exception e) {
        throw new CustomErrorException("Product service is not available", "UNAVAILABLE", 500);
    }

}
