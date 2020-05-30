package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * payuser
 * @author 
 */
@Data
public class Payuser implements Serializable {
    /**
     * 收费 id
     */
    private Integer userpayid;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 收费类型id
     */
    private Integer paymentid;

    /**
     * 收费金额
     */
    private BigDecimal paymoney;

    /**
     * 收费状态
     */
    private Integer patstate;

    private static final long serialVersionUID = 1L;
}