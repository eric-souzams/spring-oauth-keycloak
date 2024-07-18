package com.project.ProductService.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private long productId;

    private String productName;

    private double price;

    private long quantity;

}
