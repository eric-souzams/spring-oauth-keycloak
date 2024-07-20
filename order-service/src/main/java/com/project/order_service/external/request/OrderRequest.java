package com.project.order_service.external.request;

import com.project.order_service.enums.PaymentMode;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class OrderRequest {

    private long productId;
    private long quantity;
    private double totalAmount;
    private PaymentMode paymentMode;

}
