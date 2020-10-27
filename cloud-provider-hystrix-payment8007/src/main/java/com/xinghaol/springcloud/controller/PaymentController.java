package com.xinghaol.springcloud.controller;

import com.xinghaol.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lixinghao
 * @date: 2020/10/12 11:43 下午
 * @Description:
 */
@RestController
@Slf4j
@EnableHystrix
public class PaymentController {
    @Autowired
    private PaymentHystrixService paymentHystrixService;


    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentOk(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentOk(id);
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentTimeout(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentTimeout(id);
    }

    /************************************************************ 分割线-服务熔断 ************************************************************/
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentCircuitBreaker(id);
    }
}
