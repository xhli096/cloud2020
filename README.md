# cloud2020
尚硅谷spring cloud 2020学习工程

#### Hystrix之服务熔断理论
服务熔断会触发服务降级
```
服务熔断：类比于保险丝到达最大服务访问后，直接拒绝访问，然后通过调用
        服务降级方法友好返回提示；服务的降级->进而熔断->恢复调用链路。
```
- 服务熔断机制概述

熔断机制是应对雪崩效应的一种微服务链路保护机制，当扇出链路的某个微服务出错
或者不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，
快速返回错误的相应信息。当检测到该节点微服务调用响应正常后，恢复调用链路。

在spring cloud框架中，熔断机制通过Hystrix实现。Hystrix会监控微服务之间
的调用情况，当失败的调用到一定阈值，缺省是5秒内20次调用失败，就会启动熔断
机制，熔断机制的注解是@HystrixCommand。

- 实操

修改payment-hystrix-8001
```
@HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
        // 是否开启断路器
        @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
        // 请求次数
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
        // 时间窗口期
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
        // 失败率达到多少后熔断
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
})
public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
    if (id < 0) {
        throw new RuntimeException("查询id不能为负数");
    }
    String serialNumber = IdUtil.simpleUUID();
    return Thread.currentThread().getName() + "，调用成功，流水号：" + serialNumber;
}

public String paymentCircuitBreakerFallback(@PathVariable("id") Integer id) {
    return "查询id不能为负数，请稍后再试。呜呜呜~~ id = " + id;
}
```
为什么配置这些配置

[图片]