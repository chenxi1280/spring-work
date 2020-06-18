package com.work.boot.pay.config.wxpay;

import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;

import com.work.boot.pay.util.DeviceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class WxConfig implements WXPayConfig {
    public static final String APPID = "wx90b07e7298f25616";// 重庆利作网络科技有限公司
    public static final String MCH_ID = "1581527581";// 利作科技(商户id)

    public static final String DOM_URL = "http://zk688t.natappfree.cc";
//    public static final String DOM_URL = "http://q32kcn.natappfree.cc";


    public static final String NOTIFY_URL = "/pages/front/pay/wxPayRes";// 微信支付异步通知接口
    // 微信没有同步通知接口:付款之后,还得自己去查询订单状态是否改变,所以还需要自己写.


    public static final String APPSCRET = "bac1f91449a15a5cdf5665e63d5a6ade";
    public static final String MYKEY = "500222199110025451151001lhaijj13";// 这个是你自己在微信后台设置的密码

    private byte[] certData;
    public static WxConfig wxConfig;

    static {
        try {
            wxConfig = new WxConfig();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("微信证书读取失败，请联系管理员！");
        }
    }

    public WxConfig() throws IOException {// 还需要加载微信颁发的证书,在登录商户后台可以下载
        //必须这样才能读取到jar包中的文件，如果放在classPath下面，那么在程序之中要读取这个文件，就需要用到ClassPathResource类
        ClassPathResource resource = new ClassPathResource("apiclient_cert.p12");
        InputStream certStream = resource.getInputStream();//要的就是输入流
        this.certData = new byte[1024];
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
        int rc = 0;
        while ((rc = certStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        this.certData = swapStream.toByteArray(); //in_b为转换之后的结果
        certStream.read(this.certData);
        certStream.close();
    }

    @Override
    public String getAppID() {
        return APPID;
    }

    @Override
    public String getMchID() {
        return MCH_ID;
    }

    @Override
    public String getKey() {
        return MYKEY;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    /**
     * 告诉微信异步通知的时候，告诉微信我收到了，你不要再给我发用户付款成功的消息了
     *
     * @return
     */
    public static String notifyWxImGet() {
        Map<String, String> resMap = new HashMap<>();
        resMap.put("return_code", "SUCCESS");
        resMap.put("return_msg", "OK");
        //将map转换成xml字符串返回给微信，我收到了，你别再发了
        try {
            return WXPayUtil.mapToXml(resMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获取微信返回的数据 xml 变成map
    public static Map<String, String> getWxResMap(HttpServletRequest request) {

        try {
            InputStream inStream = request.getInputStream();
            if (inStream != null) {
                //将流转换成字符串,用Scanner
                Scanner s = new Scanner(inStream);
                String resStr = "";
                while (s.hasNext()) {
                    resStr += s.next();
                }
                // resStr="<xml><appid><![CDATA[wx90b07e7298f25616]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[50]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1500995971]]></mch_id><nonce_str><![CDATA[d27f6b2a957547ec8fecf8230132b870]]></nonce_str><openid><![CDATA[o_sxo1fgVOZgC9SpXe9QOLgUgqCw]]></openid><out_trade_no><![CDATA[1532079492910-814227]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[7F76BA0C13868E69D061AA8153718479]]></sign><time_end><![CDATA[20180720173839]]></time_end><total_fee>50</total_fee><trade_type><![CDATA[MWEB]]></trade_type><transaction_id><![CDATA[4200000135201807209960939374]]></transaction_id></xml>";
                //将XML格式转化成MAP格式数据
                Map<String, String> resultMap = WXPayUtil.xmlToMap(resStr);//拿到了微信过来的Map
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }


    /**
     * 基础必传参数 简单封装
     * out_trade_no:订单号
     * actuallyPaid：实际付款金额
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
    public static Map<String, String> getParameterMap(String out_trade_no, BigDecimal actuallyPaid) {
        // 定义个装参数的map
        Map<String, String> data = new HashMap<>();
        //沙箱环境
//            WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);
        data.put("appid", WxConfig.APPID);//  appid
        data.put("mch_id", WxConfig.MCH_ID);// 商户id
        data.put("nonce_str", WXPayUtil.generateNonceStr()); //随机字符串
        data.put("body", "订单付款");//描述
        data.put("out_trade_no", out_trade_no);//订单编号
        //订单有效支付时间yyyyMMddHHmmss,格式(必须)
        //29分钟后到期
        String youxiaoDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis() + 1000 * 60 * 29));
        data.put("time_expire", youxiaoDate);// 订单有效支付时间
        int zonge = actuallyPaid.multiply(new BigDecimal("100")).intValue();// int类型的金额单位是分
        data.put("total_fee", String.valueOf(zonge));//总金额
        //设置ip地址(微信支付还需要ip地址)
        data.put("spbill_create_ip", getIpAddr());
        //支付结果通知路径
        data.put("notify_url", WxConfig.DOM_URL + WxConfig.NOTIFY_URL); // 异步通知路径
        return data;
    }

    public static Map<String, String> getSingnMap(Map<String, String> map) {
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
