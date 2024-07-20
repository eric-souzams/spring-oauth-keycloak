package com.project.payment_service.service;

import com.project.payment_service.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest request);
}
