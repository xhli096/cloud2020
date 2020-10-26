package com.xinghaol.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author xinghaol
 * @version 1.0
 * @date 2020/10/26 3:57 下午
 * @description description
 */
@Component
public class PaymentHystrixFeignServiceImpl implements PaymentHystrixFeignService {
    @Override
    public String paymentOk(Integer id) {
        return "PaymentHystrixFeignServiceImpl paymentOk fallback, ┭┮﹏┭┮";
    }

    @Override
    public String paymentTimeout(Integer id) {
        return "PaymentHystrixFeignServiceImpl paymentTimeout fallback， ┭┮﹏┭┮";
    }
}
