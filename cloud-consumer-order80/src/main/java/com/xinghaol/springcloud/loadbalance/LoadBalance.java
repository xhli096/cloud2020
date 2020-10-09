package com.xinghaol.springcloud.loadbalance;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author: lixinghao
 * @date: 2020/10/8 4:12 下午
 * @Description:
 */
public interface LoadBalance {
    /**
     * 在目前可提供服务的service中选择一个
     *
     * @param serviceInstanceList
     * @return
     */
    ServiceInstance serviceInstance(List<ServiceInstance> serviceInstanceList);
}
