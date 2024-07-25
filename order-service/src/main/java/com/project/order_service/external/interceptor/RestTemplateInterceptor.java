package com.project.order_service.external.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        request.getHeaders().setBearerAuth(authentication.getToken().getTokenValue());

        return execution.execute(request, body);
    }

}