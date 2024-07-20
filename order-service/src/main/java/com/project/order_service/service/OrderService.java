package com.project.order_service.service;

import com.project.order_service.model.OrderRequest;
import com.project.order_service.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest request);

    OrderResponse getOrderDetails(long orderId);
}
