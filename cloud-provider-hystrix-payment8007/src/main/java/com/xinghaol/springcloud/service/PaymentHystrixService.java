package com.xinghaol.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @author: lixinghao
 * @date: 2020/10/12 11:40 下午
 * @Description:
 */
@Service
public class PaymentHystrixService {
    public String paymentOk(Integer id) {
        return "线程：" + Thread.currentThread().getName() + ", 方法：paymentOk，id :  " + id + ", O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentTimeoutFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentTimeout(Integer id) {
        Integer timeout = 3;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程：" + Thread.currentThread().getName() + ", 方法：paymentTimeout，id :  " + id + ", O(∩_∩)O哈哈~" + ", 耗时：" + timeout + "s";
    }

    public String paymentTimeoutFallback(Integer id) {
        return "线程：" + Thread.currentThread().getName() + ", 8001x系统繁忙或者运行报错，方法：paymentTimeoutFallback，id :  " + id + ", ┭┮﹏┭┮";
    }

    /************************************************************ 分割线-服务熔断 ************************************************************/
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            // 请求次数
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            // 失败率达到多少后熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("查询id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "，调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreakerFallback(@PathVariable("id") Integer id) {
        return "查询id不能为负数，请稍后再试。呜呜呜~~ id = " + id;
    }
}
