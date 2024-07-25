package com.project.ProductService.controller;

import com.project.ProductService.model.ProductRequest;
import com.project.ProductService.model.ProductResponse;
import com.project.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('SCOPE_internal')")
    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest request) {
        long productId = productService.addProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('CUSTOMER') || hasAuthority('SCOPE_internal')")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") long productId) {
        ProductResponse response = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PutMapping("/reduce-quantity/{productId}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("productId") long productId, @RequestParam long quantity) {
        productService.reduceQuantity(productId, quantity);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
