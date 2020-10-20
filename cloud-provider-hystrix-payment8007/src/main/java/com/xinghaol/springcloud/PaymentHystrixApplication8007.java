package com.xinghaol.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * @author: lixinghao
 * @date: 2020/10/12 11:23 下午
 * @Description:
 */
@SpringBootApplication
@EnableCircuitBreaker
public class PaymentHystrixApplication8007 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixApplication8007.class, args);
    }
}
