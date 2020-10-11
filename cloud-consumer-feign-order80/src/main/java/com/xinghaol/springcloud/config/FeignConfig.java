package com.xinghaol.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lixinghao
 * @date: 2020/10/11 3:24 下午
 * @Description:
 */
@Configuration
public class FeignConfig {
    @Bean
    public Logger.Level feignLoggerFeign() {
        return Logger.Level.FULL;
    }
}
