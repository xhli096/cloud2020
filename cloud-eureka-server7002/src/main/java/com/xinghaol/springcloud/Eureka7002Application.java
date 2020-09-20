package com.xinghaol.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: lixinghao
 * @date: 2020/9/18 9:22 下午
 * @Description:
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka7002Application {
    public static void main(String[] args) {
        SpringApplication.run(Eureka7002Application.class, args);
    }
}
