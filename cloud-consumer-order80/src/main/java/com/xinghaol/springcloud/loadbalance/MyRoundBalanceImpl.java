package com.xinghaol.springcloud.loadbalance;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: lixinghao
 * @date: 2020/10/8 4:13 下午
 * @Description: 会导致查询接口不可用，所以又将@LoadBalance注解恢复
 */
@Component
@Slf4j
public class MyRoundBalanceImpl implements LoadBalance {
    /**
     * 用于统计次数
     */
    private AtomicInteger active = new AtomicInteger(0);

    public final int getAndIncrement() {
        int current;
        int next;

        do {
            current = this.active.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        } while (!this.active.compareAndSet(current, next));
        log.info("当前次数：{}", next);
        return next;
    }

    /**
     * 在目前可提供服务的service中选择一个
     *
     * @param serviceInstanceList
     * @return
     */
    @Override
    public ServiceInstance serviceInstance(List<ServiceInstance> serviceInstanceList) {
        int index = getAndIncrement() % CollectionUtils.size(serviceInstanceList);
        return serviceInstanceList.get(index);
    }
}
