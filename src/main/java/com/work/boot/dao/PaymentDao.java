package com.work.boot.dao;

import com.work.boot.pojo.entity.Payment;
import com.work.boot.pojo.vo.PayuserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentDao {
    int deleteByPrimaryKey(Integer paymentid);

    int insert(Payment record);

    int insertSelective(Payment record);

    Payment selectByPrimaryKey(Integer paymentid);

    int updateByPrimaryKeySelective(Payment record);

    int updateByPrimaryKey(Payment record);

    List<Payment> selectByListPay(@Param("list") List<PayuserVo> list);

    List<Payment> selectAll();

}