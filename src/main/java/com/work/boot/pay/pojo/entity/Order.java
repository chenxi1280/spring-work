package com.work.boot.pay.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * creator：lhtjj
 * date: 2020/4/30
 * 需要一个自己封装的支付宝的订单参数
 */
@Data
public class Order implements Serializable {
    private Long orderId;// 单号
    private String payType;// 付款方式：// wxpay、alipay
    private String subject; // 描述
    private BigDecimal actuallyPaid;// 实际付款金额（元.角分）

    private Date createTime;// 订单的创建时间，分片需要
}
