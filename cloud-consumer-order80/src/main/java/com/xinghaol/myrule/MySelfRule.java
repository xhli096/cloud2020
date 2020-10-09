package com.xinghaol.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lixinghao
 * @date: 2020/10/5 12:02 下午
 * @Description:
 */
@Configuration
public class MySelfRule {
    /**
     * 变为随机算法
     *
     * @return
     */
    @Bean
    public IRule rule() {
        return new RandomRule();
    }
}
