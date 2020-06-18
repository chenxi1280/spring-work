package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname Order
 * @Description TODO
 * @Date 2020/6/16 20:34
 * @CreateComputer by PC
 * @Created by cxd
 */
@Data
public class Order implements Serializable {
    private Long orderId;// 单号
    private String payType;// 付款方式：// wxpay、alipay
    private String subject; // 描述
    private BigDecimal actuallyPaid;// 实际付款金额（元.角分）

    private Date createTime;// 订单的创建时间，分片需要
}
