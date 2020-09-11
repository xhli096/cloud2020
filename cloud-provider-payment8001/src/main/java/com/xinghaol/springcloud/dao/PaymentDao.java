package com.xinghaol.springcloud.dao;

import com.xinghaol.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: lixinghao
 * @date: 2020/9/7 11:40 下午
 * @Description:
 */
@Mapper
public interface PaymentDao {
    int insert(Payment payment);

    Payment queryPaymentById(@Param("id") Long id);
}
