package com.xinghaol.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: lixinghao
 * @date: 2020/10/4 7:51 下午
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentConsulApplication8006 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentConsulApplication8006.class, args);
    }
}
