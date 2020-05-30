package com.work.boot.dao;

import com.work.boot.pojo.entity.Payment;

public interface PaymentDao {
    int deleteByPrimaryKey(Integer paymentid);

    int insert(Payment record);

    int insertSelective(Payment record);

    Payment selectByPrimaryKey(Integer paymentid);

    int updateByPrimaryKeySelective(Payment record);

    int updateByPrimaryKey(Payment record);
}