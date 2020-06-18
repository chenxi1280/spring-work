package com.work.boot.pay.pojo.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：森林cxd
 * 一定要有一个阿里付款之后的回调结果类
 */
@Data
public class AliPayResDTO {

//    private Date gmt_create;// 订单创建时间
    private String charset;// 字符编码
//    private Date gmt_payment;// 付款时间
//    private Date notify_time;//
    private String subject;//描述
    private String sign;//签名
    private String buyer_id;//买家的支付宝用户 id，如果为空，会从传入的码值信息中获取买家 ID
    private BigDecimal invoice_amount;//交易中可给用户开具发票的金额
    private String version;
    private String notify_id;
    private String notify_type;
    private Long out_trade_no;//商户订单号
    private BigDecimal total_amount;//交易金额
    private String trade_status;
    private String trade_no;//支付宝交易号
    private String auth_app_id;
    private BigDecimal receipt_amount;// 实收金额
    private BigDecimal point_amount;// 使用集分宝付款的金额
    private BigDecimal buyer_pay_amount;//买家付款的金额
    private String app_id;//支付宝分配给开发者的应用ID
    private String sign_type;//商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
    private String seller_id;//如果该值为空，则默认为商户签约账号对应的支付宝用户ID

}
