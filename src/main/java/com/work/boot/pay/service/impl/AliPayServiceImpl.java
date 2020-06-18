package com.work.boot.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayResponse;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;

import com.work.boot.pay.config.alipay.AlipayConfig;
import com.work.boot.pay.pojo.entity.Order;
import com.work.boot.pay.service.AliPayService;
import com.work.boot.pay.util.DeviceUtil;
import com.work.boot.pojo.dto.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * creator：cxd
 * date: 2020/6/12
 */
@Service
public class AliPayServiceImpl implements AliPayService {
    @Override
    public ResponseDTO unifiedPay(Order od) throws Exception {
        Assert.notNull(od.getOrderId(), "单号不存在");
        Assert.notNull(od.getActuallyPaid(), "实际付款不能为空");
        Assert.notNull(od.getPayType(), "付款方式不能为空");
        Assert.notNull(od.getSubject(), "描述不能为空");
        Map<String, Object> paramerMap = new HashMap<>();// 定义请求参数集
        paramerMap.put("out_trade_no", od.getOrderId());// 交易单号！
        paramerMap.put("total_amount", od.getActuallyPaid());// 商品总金额：单位：元
        paramerMap.put("subject", od.getSubject());// 描述
        paramerMap.put("timeout_express", "2m");// 只能2分钟内完成支付，不然达达订单就过期了
        paramerMap.put("product_code", "FAST_INSTANT_TRADE_PAY");// 固定的

        String content = JSON.toJSONString(paramerMap);// 把请求参数集合变成JSON字符串

        AlipayResponse res;// 顶一个字符串表单结果
        if (DeviceUtil.isMobile()) {// 手机：吊起支付宝：通过头信息里边有个user-agent判断是不是手机
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
            //设置支付宝异步通知地址：支付结果通知
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
            //设置支付宝同步通知地址:重定向地址
            alipayRequest.setReturnUrl(AlipayConfig.return_url);

            //业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递
            alipayRequest.setBizContent(content);//  请求参数的JSON字符串

            //发送请求，支付宝将返回一个支付请求的（表单数据串）"<form></form>"，还需要把这个表单渲染到你自己的页面去
            res = AlipayConfig.getAlipayClient().pageExecute(alipayRequest);
            if (res.isSuccess()) {
                return ResponseDTO.ok(res.getMsg(), res.getBody());
            }
        } else {//  电脑：出现扫码
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            //设置支付宝同步通知地址
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            //设置支付宝异步通知地址
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
            //业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递
            alipayRequest.setBizContent(content);//销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY
//                    发送请求，支付宝将返回一个支付请求的表单数据串 ,这个是html 文档字符串是一个表单
            res = AlipayConfig.getAlipayClient().pageExecute(alipayRequest);
        }
        if (res.isSuccess()) {
            return ResponseDTO.ok(res.getMsg(), res.getBody());
        } else {
            return ResponseDTO.fail(res.getMsg());
        }
    }
}
