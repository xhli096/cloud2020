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

- 熔断类型

1、熔断打开：请求不在进行调用当前服务，内部设置时钟一般为MTTR(平均故障处理时间)，当打开时长达到所
设时钟则进入班熔断状态；

2、熔断关闭：熔断关闭不会对服务进行熔断；

3、熔断半开：部分请求根据规则调用当前服务，如果请求成功且符合规则，则认为当前服务恢复正常，关闭熔断。

- 涉及到断路器的三个重要参数

1、快照时间窗：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近
的10秒。

2、请求总数阈值：在快照时间窗内，必须满足请求总数阈值才有资格熔断，默认为20，意味着在10秒内，如果
该hystrix命令的调用次数不足20次，即使所有的请求都是超时或者其他原因失败，断路器也不会打开。

3、错误百分比阈值：在请求总数在快照时间窗内超过了阈值，比如发生了30次调用，如果在这30次调用中，有15
次发生了超时异常，也就是超过了50%的错误百分比，在默认设定50%的阈值情况下，这时候就会打开断路器。

- 断路器打开后

1、再有请求调用的时候，将不会调用主逻辑，直接调用降级fallback逻辑。通过断路器，实现了自动地发现
错误并将降级逻辑切换为主逻辑，减少响应的效果。

2、原来的主逻辑要如何恢复？

对于这一问题，hystrix也实现了自动恢复功能；当断路器打开，对主逻辑进行熔断之后，hystrix会启动一个
休眠时间窗，在这个时间窗内，降级逻辑是临时的称为主逻辑；当休眠时间窗到期，断路器进入半开状态，释放
一次请求到原来的主逻辑上，如果此次请求正常返回，那么断路器将继续闭合，主逻辑恢复；如果这次请求依然
有问题，断路器继续进入打开状态，休眠时间窗重新计时。

#### 服务限流
后面高级篇讲解 阿里巴巴sentinel

#### hystrix的工作流程
流程图 -》 网上去找一下


#### 服务监控hystrixDashBoard
仪表盘-9001
主启动类添加 ： @EnableHystrixDashboard注解