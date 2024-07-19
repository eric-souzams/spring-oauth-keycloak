package com.project.order_service.external.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private String errorMessage;
    private String errorCode;

}
