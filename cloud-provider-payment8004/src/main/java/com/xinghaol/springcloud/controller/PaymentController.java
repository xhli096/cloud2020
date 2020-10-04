package com.xinghaol.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author: lixinghao
 * @date: 2020/10/3 11:17 下午
 * @Description:
 */
@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/zk")
    public String paymentZookeeper() {
        return "spring cloud with zookeeper, serverPort：" + serverPort + ", " + UUID.randomUUID().toString();
    }
}
