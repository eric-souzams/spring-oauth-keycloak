package com.project.order_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.order_service.enums.PaymentMode;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDetailsResponse {

    private long paymentId;

    private String paymentStatus;

    private PaymentMode paymentMode;

    private Instant paymentDate;

    @JsonIgnore
    private double totalAmount;

    @JsonIgnore
    private long orderId;

}
