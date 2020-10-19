package com.xinghaol.springcloud.order;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xinghaol.springcloud.entities.CommonResult;
import com.xinghaol.springcloud.entities.Payment;
import com.xinghaol.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: lixinghao
 * @date: 2020/10/9 11:38 下午
 * @Description:
 */
@RestController
@Slf4j
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/openfeign/get/{id}")
    public CommonResult<Payment> paymentFeign(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/consumer/openfeign/timeout")
    @HystrixCommand(fallbackMethod = "paymentFeignTimeoutFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    public String paymentFeignTimeout() {
        // openfeign-ribbon 一般都是默认等待1秒钟
        return paymentFeignService.paymentFeignTimeout();
    }


    public String paymentFeignTimeoutFallback() {
        return "消费者80，通过feign接口调用，对方系统繁忙，或者自己运行出错。┭┮﹏┭┮！！！";
    }
}
