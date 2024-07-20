package com.project.payment_service.controller;

import com.project.payment_service.model.PaymentRequest;
import com.project.payment_service.model.PaymentResponse;
import com.project.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request) {
        long paymentId = paymentService.doPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentId);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable("orderId") long orderId) {
        PaymentResponse paymentDetails = paymentService.getPaymentDetailsByOrderId(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(paymentDetails);
    }

}
