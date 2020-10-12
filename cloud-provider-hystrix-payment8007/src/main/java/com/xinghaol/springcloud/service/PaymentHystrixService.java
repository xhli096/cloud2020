package com.xinghaol.springcloud.service;

import org.springframework.stereotype.Service;

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

    public String paymentTimeout(Integer id) {
        Integer  timeout = 5;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程：" + Thread.currentThread().getName() + ", 方法：paymentTimeout，id :  " + id + ", O(∩_∩)O哈哈~" + ", 耗时：" + timeout + "s";
    }
}
