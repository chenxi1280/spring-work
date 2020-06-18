package com.work.boot.pay.service;


import com.work.boot.pay.pojo.entity.Order;
import com.work.boot.pojo.dto.ResponseDTO;

/**
 * creator：cxd
 * date: 2020/6/12
 */
public interface AliPayService {

    ResponseDTO unifiedPay(Order od) throws Exception;

}
