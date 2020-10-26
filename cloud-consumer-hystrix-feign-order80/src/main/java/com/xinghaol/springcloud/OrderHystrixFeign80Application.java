package com.xinghaol.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xinghaol
 * @version 1.0
 * @date 2020/10/26 10:58 上午
 * @description description
 */
@SpringBootApplication
@EnableHystrix
@EnableFeignClients
public class OrderHystrixFeign80Application {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixFeign80Application.class, args);
    }
}