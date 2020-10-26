package com.xinghaol.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xinghaol.springcloud.service.PaymentHystrixFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xinghaol
 * @version 1.0
 * @date 2020/10/26 11:13 上午
 * @description @DefaultProperties注解将一些统一的降级收到一个方法里面
 */
@RestController
@DefaultProperties(defaultFallback = "paymentTimeoutDefault")
public class OrderHystrixFeignController {
    @Resource
    private PaymentHystrixFeignService paymentHystrixFeignService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentOk(@PathVariable("id") Integer id) {
        return paymentHystrixFeignService.paymentOk(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentTimeoutHystrix", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
//    })
//    @HystrixCommand
    public String paymentTimeout(@PathVariable("id") Integer id) {
        return paymentHystrixFeignService.paymentTimeout(id);
    }

    public String paymentTimeoutHystrix(@PathVariable("id") Integer id) {
        return "我是消费者80，对方支付系统繁忙，请10秒后再试或者自己运行出错请检查自己服务状态，id：" + id + "，┭┮﹏┭┮！！！";
    }

    /**
     * 通用兜底方法的方法定义貌似要是无参的
     *
     * @return
     */
    public String paymentTimeoutDefault() {
        return "通用降级处理异常，请稍后再试。┭┮﹏┭┮！！！";
    }
}
