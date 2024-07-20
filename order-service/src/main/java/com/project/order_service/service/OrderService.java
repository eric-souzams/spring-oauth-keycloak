package com.project.order_service.service;

import com.project.order_service.external.request.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest request);
}
