package com.project.order_service;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class TestServiceInstanceListSupplier implements ServiceInstanceListSupplier {
    @Override
    public String getServiceId() {
        return "";
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        serviceInstances.add(new DefaultServiceInstance("PAYMENT-SERVICE", "PAYMENT-SERVICE", "localhost", 8080, false));
        serviceInstances.add(new DefaultServiceInstance("PRODUCT-SERVICE", "PRODUCT-SERVICE", "localhost", 8080, false));

        return Flux.just(serviceInstances);
    }
}
