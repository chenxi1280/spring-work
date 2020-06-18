package com.work.boot.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.work.boot.pay.config.alipay.AlipayConfig;
import com.work.boot.pay.pojo.dto.AliPayResDTO;
import com.work.boot.pojo.entity.Payuser;
import com.work.boot.service.PayuserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname PayController
 * @Description TODO
 * @Date 2020/6/17 20:11
 * @CreateComputer by PC
 * @Created by cxd
 */
@Controller
public class PayController {
    @Resource
    private PayuserService payuserService;
    // 支付宝的通知的异步接口
    @RequestMapping("/pay/aliPayResYb")
    String aliPayResYb(HttpServletRequest request, AliPayResDTO aliPayRes) {

        Map<String, String> params = new HashMap<>();//先把请求参数封装为map集合
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String pName = names.nextElement();
            params.put(pName, request.getParameter(pName));
        }
        try {
            boolean signRes;
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            //这里必须要协商signType,验证签名
            signRes = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "utf-8", "RSA2");
            String appid = request.getParameter("app_id");
            if (AlipayConfig.app_id.equals(appid) && signRes) { //首先判断appid是不是正确的，再看签名是不是来自于支付宝
                if ("TRADE_SUCCESS".equals(aliPayRes.getTrade_status())) {// 交易状态

                    Payuser payuser = new Payuser();
                    payuser.setUserpayid(Integer.valueOf(Math.toIntExact(aliPayRes.getOut_trade_no())));
                    payuser.setPatstate(1);
                    payuserService.paySuccess(payuser);

                    return "success";// 也要告诉支付宝我收到你的信息了,你就不要给我发消息了

                    // 发消息给商户websocket

                } else if ("TRADE_CLOSED".equals(aliPayRes.getTrade_status())) {// 退款通知接口
//                        mymorder.setZfRes("已退款");
//                        if (morderService.updatePayRes(mymorder)) {//退款成功后
//                            System.err.println("已收到通知-已退款");
////                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }
}
