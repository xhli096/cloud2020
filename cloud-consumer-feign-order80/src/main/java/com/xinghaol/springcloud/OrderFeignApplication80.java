package com.xinghaol.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: lixinghao
 * @date: 2020/10/9 11:07 下午
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class OrderFeignApplication80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignApplication80.class, args);
    }
}
