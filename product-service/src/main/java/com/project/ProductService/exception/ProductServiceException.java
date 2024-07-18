package com.project.ProductService.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductServiceException extends RuntimeException {

    private String errorCode;

    public ProductServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
