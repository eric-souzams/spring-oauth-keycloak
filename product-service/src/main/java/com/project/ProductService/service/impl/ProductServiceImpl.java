package com.project.ProductService.service.impl;

import com.project.ProductService.entity.Product;
import com.project.ProductService.exception.ProductServiceException;
import com.project.ProductService.model.ProductRequest;
import com.project.ProductService.model.ProductResponse;
import com.project.ProductService.repository.ProductRepository;
import com.project.ProductService.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Override
    public long addProduct(ProductRequest request) {
        log.info("Adding product...");

        Product product = Product.builder()
                .productName(request.getProductName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        productRepository.save(product);

        log.info("Product created...");

        return product.getProductId();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Getting product by id: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceException("Product with given id not found.", "PRODUCT_NOT_FOUND"));

        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response);

        return response;
    }

    @Transactional
    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reducing quantity {} from product {}", quantity, productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceException("Product with given id not found.", "PRODUCT_NOT_FOUND"));

        if (product.getQuantity() < quantity) {
            throw new ProductServiceException("Product does not have sufficient quantity.", "INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity() - quantity);

        productRepository.save(product);

        log.info("Product {} quantity updated with successfully", productId);
    }
}
