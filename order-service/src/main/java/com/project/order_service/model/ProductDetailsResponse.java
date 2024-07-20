package com.project.order_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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