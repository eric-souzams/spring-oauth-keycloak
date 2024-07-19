package com.project.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "order_details")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "total_amount", precision = 2)
    private double totalAmount;

}
