package com.project.order_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsResponse {

    private long productId;

    private String productName;

    @JsonIgnore
    private double price;

    @JsonIgnore
    private long quantity;

}
