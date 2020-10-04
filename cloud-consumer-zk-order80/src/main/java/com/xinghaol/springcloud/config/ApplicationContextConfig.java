package com.xinghaol.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: lixinghao
 * @date: 2020/10/4 12:49 上午
 * @Description:
 */
@Configuration
public class ApplicationContextConfig {
    /**
     * 实例化restTemplate
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
