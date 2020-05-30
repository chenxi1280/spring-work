package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * payment
 * @author 
 */
@Data
public class Payment implements Serializable {
    /**
     * 缴费id
     */
    private Integer paymentid;

    /**
     * 缴费金额
     */
    private String paymentname;

    /**
     * 每月收取的金额
     */
    private BigDecimal paymentmoney;

    private static final long serialVersionUID = 1L;
}