package com.project.ProductService.service;

import com.project.ProductService.model.ProductRequest;
import com.project.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest request);

    ProductResponse getProductById(long productId);
}
