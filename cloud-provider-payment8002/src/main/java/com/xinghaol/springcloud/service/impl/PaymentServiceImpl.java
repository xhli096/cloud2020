package com.xinghaol.springcloud.service.impl;

import com.xinghaol.springcloud.dao.PaymentDao;
import com.xinghaol.springcloud.entities.Payment;
import com.xinghaol.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: lixinghao
 * @date: 2020/9/8 11:41 下午
 * @Description:
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int insert(Payment payment) {
        return paymentDao.insert(payment);
    }

    @Override
    public Payment queryPaymentById(Long id) {
        return paymentDao.queryPaymentById(id);
    }
}
