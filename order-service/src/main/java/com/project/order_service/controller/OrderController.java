package com.project.order_service.controller;

import com.project.order_service.model.OrderRequest;
import com.project.order_service.model.OrderResponse;
import com.project.order_service.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @PostMapping("/place-order")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest request) {
        long orderId = orderService.placeOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable("orderId") long orderId) {
        OrderResponse order = orderService.getOrderDetails(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

}
