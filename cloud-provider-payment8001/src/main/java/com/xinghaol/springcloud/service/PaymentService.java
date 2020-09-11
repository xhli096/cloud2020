package com.xinghaol.springcloud.service;

import com.xinghaol.springcloud.entities.Payment;

/**
 * @author: lixinghao
 * @date: 2020/9/8 11:41 下午
 * @Description:
 */
public interface PaymentService {
    int insert(Payment payment);

    Payment queryPaymentById(Long id);
}
