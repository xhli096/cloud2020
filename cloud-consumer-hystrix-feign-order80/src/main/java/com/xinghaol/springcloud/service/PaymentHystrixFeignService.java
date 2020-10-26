package com.xinghaol.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xinghaol
 * @version 1.0
 * @date 2020/10/26 11:11 上午
 * @description description
 */
@Primary
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = PaymentHystrixFeignServiceImpl.class)
public interface PaymentHystrixFeignService {
    @GetMapping("/payment/hystrix/ok/{id}")
    String paymentOk(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    String paymentTimeout(@PathVariable("id") Integer id);
}
