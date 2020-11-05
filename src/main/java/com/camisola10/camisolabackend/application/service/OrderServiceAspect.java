package com.camisola10.camisolabackend.application.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
class OrderServiceAspect {

    @AfterThrowing(pointcut = "execution(* com.camisola10.camisolabackend.application.service.OrderService.createOrder(..))", throwing = "exception")
    public void afterThrowingAdvice(Throwable exception) {
        //TODO: add prometheus
        log.error("Error creating order", exception);
    }
}
