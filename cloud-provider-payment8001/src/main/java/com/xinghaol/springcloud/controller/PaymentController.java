package com.xinghaol.springcloud.controller;

import com.alibaba.fastjson.JSON;
import com.xinghaol.springcloud.entities.CommonResult;
import com.xinghaol.springcloud.entities.Payment;
import com.xinghaol.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author: lixinghao
 * @date: 2020/9/8 11:43 下午
 * @Description:
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult insert(Payment payment) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1,1);
        log.info("插入payment={}", JSON.toJSONString(payment));
        int result = paymentService.insert(payment);
        log.info("插入结果result={}", result);
        if (result > 0) {
            return new CommonResult(200, "插入数据成功", result);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        log.info("查询参数id={}", id);
        int a = 10;
        Payment payment = paymentService.queryPaymentById(id);
        log.info("查询结果result={}", JSON.toJSONString(payment));
        if (Objects.nonNull(payment)) {
            return new CommonResult(200, "查询成功", payment);
        } else {
            return new CommonResult(444, "未查询到对应记录，查询ID：" + id, null);
        }
    }

}
