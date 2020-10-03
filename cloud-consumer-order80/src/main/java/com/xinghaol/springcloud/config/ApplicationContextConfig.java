package com.xinghaol.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: lixinghao
 * @date: 2020/9/13 9:31 下午
 * @Description: bean配置类
 */
@Configuration
public class ApplicationContextConfig {
    /**
     * 使用@LoadBalanced注解使得RestTemplate支持负载均衡
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
