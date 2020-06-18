package com.work.boot.pay.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;

import com.work.boot.pay.config.wxpay.WxConfig;
import com.work.boot.pay.pojo.entity.Order;
import com.work.boot.pay.service.WxPayService;
import com.work.boot.pay.util.DeviceUtil;
import com.work.boot.pojo.dto.ResponseDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * creator：cxd
 * date: 2020/6/12
 */
@Service
public class WxPayServiceImpl implements WxPayService {
    @Override
    public ResponseDTO unifiedPay(Order od) throws Exception {
        WXPay wxpay = new WXPay(WxConfig.wxConfig);// 创建一个微信支付的客户端
        // 定义个装参数的map(基础参数)
        Map<String, String> data = getParameterMap(od);
        try {
            if (DeviceUtil.isMobile()) {// 手机 普通网站吊起微信支付
                data.put("scene_info", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"" + WxConfig.DOM_URL + "\",\"wap_name\": \"闪购\"}}");
                data.put("trade_type", "MWEB");  // 此处指定为H5支付。
                data = getSingnMap(data);

                Map<String, String> resp = wxpay.unifiedOrder(data);// 统一下单

                String url = resp.get("mweb_url");// 这个就是二维码地址:  wx://dshfgyuwegh.dgtyw,需要把这个地址在前端用二维码技术,转换成二维码

                if (url == null) {
                    return ResponseDTO.fail("无法调起微信支付,请在微信内完成付款");
                }
                return ResponseDTO.ok("成功", url);// 只需要前端重定向到这个地址,微信支付就会被吊起来
            } else {// 电脑支付
                data.put("trade_type", "NATIVE");// 此处指定为扫码支付。
                data = getSingnMap(data);
                //正式签名
                Map<String, String> resp = wxpay.unifiedOrder(data);// 统一下单
                String url = resp.get("code_url");// 获取二维码链接地址
                Map<String, Object> resMap = new HashMap<>();
                if ("SUCCESS".equals(resp.get("result_code"))) {// 成功
                    // 格式：weixin://sdhdfcuishusdi
                    resMap.put("qrcodeUrl", url);
                    resMap.put("allCost", od.getActuallyPaid());
                    resMap.put("orderId", od.getOrderId());
                    resMap.put("createTime", od.getCreateTime());
                    return ResponseDTO.ok("成功", resMap);
                } else {
                    resMap.put("allCost", od.getActuallyPaid());
                    resMap.put("orderId", od.getOrderId());
                    resMap.put("createTime", od.getCreateTime());
                    return ResponseDTO.fail(resp.get("return_msg"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("微信统一下单异常");
        }
    }

    /**
     * 基础必传参数简单封装
     * <p>
     * appid            应用id
     * mch_id           商户id
     * nonce_str        随机字符串
     * body             主体
     * out_trade_no     订单号
     * time_expire      过期时间默认29分钟
     * total_fee        总金额，单位是分，传入BigDecimal
     * spbill_create_ip 用户ip地址
     * notify_url       微信异步通知地址
     */
    private Map<String, String> getParameterMap(Order order) {

        // 定义个装参数的map
        Map<String, String> data = new HashMap<>();
        //沙箱环境
//            WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);
        data.put("appid", WxConfig.APPID);//  appid
        data.put("mch_id", WxConfig.MCH_ID);// 商户id
        data.put("nonce_str", WXPayUtil.generateNonceStr()); //随机字符串
        data.put("body", order.getSubject());//描述
        data.put("out_trade_no", String.valueOf(order.getOrderId()));//订单编号
        //订单有效支付时间yyyyMMddHHmmss,格式(必须)
        //2分钟后到期，不然达达订单就过期了
        String youxiaoDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis() + 1000 * 60 * 2));
        data.put("time_expire", youxiaoDate);// 订单有效支付时间

        int zonge = order.getActuallyPaid().multiply(new BigDecimal("100")).intValue();// int类型的金额单位是分

        data.put("total_fee", String.valueOf(zonge));//总金额
        //设置ip地址
        data.put("spbill_create_ip", getIpAddr());
        //支付结果通知路径
        data.put("notify_url", WxConfig.DOM_URL + WxConfig.NOTIFY_URL); // 异步通知路径
        return data;
    }

    private Map<String, String> getSingnMap(Map<String, String> map) {
        try {
            map.put("sign", WXPayUtil.generateSignature(map, WxConfig.MYKEY));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获得客户端访问的IP地址
    public static String getIpAddr() {
        HttpServletRequest request = DeviceUtil.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
