package com.project.payment_service.entity;

import com.project.payment_service.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Table(name = "transaction_details")
@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "order_id")
    private long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentMode paymentMode;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "total_amount")
    private double totalAmount;

}
