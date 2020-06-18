package com.work.boot.pay.config.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 支付宝的配置类
 */
public class AlipayConfig {
    public final static String DOM_URL = "http://2kf4w7.natappfree.cc"; // 不要以 / 结尾

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016102502320704";

    // 商户私钥，您的PKCS8格式RSA2私钥,需要下载阿里密钥生成工具生成而来，而且需要把密码设置到阿里账户上面去
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVSm1LR3w1dzjv9w+8rv8GO07xeycmgsZz89XgauMNd0vZlWCVydzIYaDybuvMwbChS/CdNSHP1GKlBB+8vsLXM73gdwj56UcKzeTsq3HDgv5lElykSx30JjSdLLTmEzA0ubhMlBQkVrGctXG/MtiVG679FQKQBIjzyMQZJg+nh6phWa93qArosHLCfVrqA78pHpzCCq7EGvoxk0So7lU15VH4x2Nxz6D5HPFULLuI94jx6W4GEzd8rXU0pCBnNnGmHgjunfMvCqhKLRFngUysVlFclqUt/rrKnLKDIZ2K5Tlm3tkBPLz13Wzmk9n33vPe6c0AOfJ3R6G0Dfmbm+f7AgMBAAECggEAdnViF9OXKU16rl4Z7Jvm2+j/kPkpbZNjKOqYEMR/eVXpp7Kxuuk7+PwOm4+MJyKhprr9J6DbPW/G/i0xibDMJbp2tb9+Ld7+cgJB3dGEKaCtf0F8pRi9odcW9Ihe2hjrrUqYhJsTCGqJ8FDA+ve7h0U6KrMbH2+Zw+bQQyxCS6lRjY6YDwXMLOFwxF3uAA5BMklhktbkNeYsJvAoehuROslWInpsvf7XZaZT+lBYV1i6O+7vzG7bveU/A5kHv72h4RCbKlsATXuGC4r5TaRG0Ocd7FEjoBgzePFzj/WQG+OeksjXiDOtd8ycJe9Jp/T3TIM9+1lF4x0dCSES6yvAwQKBgQDQtUWlNi2owOT02nkiY+ECEmUOQZHZxvk76Srrc5j32sVEXEa/I81TyOSJCs/SimB8L/iJpPR8d2VvCWMWgzsfQiNalPvUYTPxMzN8rcUntGJ9fDro7+etXKEEtrQrqyPH+1SrW+2kXkv1YKvhlQs43Tzu9fglolomKTQoZhq5KwKBgQC3HnnCN1Po+utz9APazVbOiGa8E6yEYyvp6fIahNuJS8DPeb6BtGCoFQcU9WgK4BUjC4/afMsUecMIYU6hB30XTXgqEUGUSpNh3AXz82ne8In2OKPirKOm+ov01rX37U4EzKvUYPkscZe6o5gLVUGsDaotULoW9/G0qBKT8AKEcQKBgBY86mHTJwRPIVYxnSyb5dK5sQjW1bqVp+LJUmhqFcd7CP/isSsWnn8p7afofgJZ+jPhTKMl72L/udAd+O/iJb+L5YZ2T2ruvIEbyoWjYll7r+7c+M3vGglv0DxXV1U71AEv93KWxfh4j7Fijo3nup1hfWecz8b6yI4+X69aScinAoGAUpVX7BLvsL6YuS5vHppEb9hsHJ3yy7G9+Dwdh4ArUfvr9KW/GNe0T5lMHFOgnGb6oVdxhOOK4q0XHAxmXZD3BS9W5gHqRWpslVGZDa/VK0nnrjdVEcEx/Pg5bi322t0WtmtngtxJA5YnC3ED/PhyKZnwCYfCpOElDIhP+I/hUsECgYEAwm9tfYfsq6VKsxY7RY65SLHh3sh9F7BLTlIKeX/YxNGf5C/81tMJj5T68YTeID48p4FmvDai1WO/o/bqD9hqo+mtdBuxSYnFbtiMYDriMGJ1Tmw1OepRF9lpQZiICCucNY0LncSXEg07abp/AE7j1pPC6zKg9Yuk25eUnQTb2uI=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtSjmMYfcyqoN1rK9/hnGfhFLQAQSKmjZ4FsODEG8Oe0dVcX7nddLpesXXSB6UYv42Hx8Y89eXeMmt6XfRzLp2Mhscb0XMJ7mauPe4jkT+Z5Eih2JjlX6jO+Eidd0XX9UV0UA8EqWK+ABGz8Iih6AfHnIO+NgvMhABD1wcfCNi7WeIIrcECb4aPRVIFhm2IDx2Mng0nhm93RuykO64hAzlGWYaevKJlyI41FrWKJ+ufczOMFRMpEZH7rDKWDIJJh11MQaeD6FdQPWkbyLYwckkWcES5x+/q52C1OeoXjgjECDTVZ4WWkKwCDRyVDrLnn+5h1sR1xmTTJKufK5E8MXXwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 异步通知接口：不是前台通知接口，需要加端口和api(域名+路径)：我们的服务器接收支付宝收到的款的通知：收到款的通知之后，修改订单付款状态，一定带有订单编号，付款金额，sign(签名)
    public static String notify_url = DOM_URL + "/payuser/aliPayResYb";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = DOM_URL + "/payuser/aliPayResTb";// 订单列表页面，用付款完成之后，用户跳转的路径
    // 相当于传递了一个参数叫redirectUrl: 当支付宝收款之后，就会进行地址重定向！

    // 签名方式（官方推荐:更加安全）
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关（固定的）
    public static String server_url = "https://openapi.alipay.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";
    // 支付宝网关
    public static String getAlipay_public_key = "C:\\";

    private static AlipayClient alipayClient;

    public static AlipayClient getAlipayClient() {
        if (alipayClient == null) {
            alipayClient = new DefaultAlipayClient(server_url, app_id,
                    merchant_private_key, "json", charset, alipay_public_key, "RSA2");
        }
        return alipayClient;
    }

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


