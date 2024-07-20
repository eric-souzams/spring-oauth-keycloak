package com.project.payment_service.service.impl;

import com.project.payment_service.entity.TransactionDetails;
import com.project.payment_service.exception.CustomException;
import com.project.payment_service.model.PaymentRequest;
import com.project.payment_service.model.PaymentResponse;
import com.project.payment_service.repository.TransactionDetailsRepository;
import com.project.payment_service.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Transactional
    @Override
    public long doPayment(PaymentRequest request) {
        log.info("Recording payment details from order {}...", request.getOrderId());

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(request.getPaymentMode())
                .paymentStatus("SUCCESS")
                .orderId(request.getOrderId())
                .referenceNumber(request.getReferenceNumber())
                .totalAmount(request.getTotalAmount())
                .build();

        transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction completed with id {}...", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Getting payment details for order {}...", orderId);

        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException("Transaction with given order id " + orderId +" not found.", "TRANSACTION_NOT_FOUND"));

        return PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentMode(transactionDetails.getPaymentMode())
                .paymentDate(transactionDetails.getPaymentDate())
                .orderId(transactionDetails.getOrderId())
                .paymentStatus(transactionDetails.getPaymentStatus())
                .totalAmount(transactionDetails.getTotalAmount())
                .build();
    }
}
