package com.xinghaol.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author: lixinghao
 * @date: 2020/10/4 8:09 下午
 * @Description:
 */
@RestController
@Slf4j
public class OrderConsulController {
    private static final String INVOKE_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/consul")
    public Object paymentInfo() {
        String res = restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);

        return res;
    }
}
