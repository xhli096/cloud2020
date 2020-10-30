package com.xinghaol.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author: lixinghao
 * @date: 2020/10/12 11:23 下午
 * @Description:
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
public class PaymentHystrixApplication8007 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixApplication8007.class, args);
    }

    /**
     * 此配置为了服务监控而配置，与服务容错本身无关，spring cloud升级的坑。
     * ServletRegistrationBean因为spring boot的默认路径不是"/hystrix.stream"，只要在自己的项目里配置servlet就可以了
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(hystrixMetricsStreamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");

        return registrationBean;
    }
}
