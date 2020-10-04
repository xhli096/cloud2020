package com.xinghaol.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: lixinghao
 * @date: 2020/10/4 12:48 上午
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderZkMainApplication80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderZkMainApplication80.class, args);
    }
}
