package com.project.order_service.external.request;

import com.project.order_service.enums.PaymentMode;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentRequest {

    private long orderId;

    private PaymentMode paymentMode;

    private String referenceNumber;

    private Instant paymentDate;

    private String paymentStatus;

    private double totalAmount;

}
