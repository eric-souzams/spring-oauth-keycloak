package com.project.order_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomErrorException extends RuntimeException {

    private String errorCode;
    private int status;

    public CustomErrorException(String message, String errorCode, int status) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}
