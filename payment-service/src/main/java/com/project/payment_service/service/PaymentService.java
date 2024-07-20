package com.project.payment_service.service;

import com.project.payment_service.model.PaymentRequest;
import com.project.payment_service.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest request);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
