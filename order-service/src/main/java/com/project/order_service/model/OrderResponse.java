package com.project.order_service.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private long orderId;
    private Instant orderDate;
    private double totalAmount;
    private String orderStatus;
    private ProductDetailsResponse productDetails;
    private TransactionDetailsResponse transactionDetails;

}
