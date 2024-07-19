package com.project.order_service.service;

import com.project.order_service.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest request);
}
