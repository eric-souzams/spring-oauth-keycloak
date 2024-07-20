package com.project.payment_service.model;

import com.project.payment_service.enums.PaymentMode;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponse {

    private long paymentId;

    private long orderId;

    private PaymentMode paymentMode;

    private String referenceNumber;

    private Instant paymentDate;

    private String paymentStatus;

    private double totalAmount;

}
