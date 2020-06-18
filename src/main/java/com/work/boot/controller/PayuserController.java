package com.work.boot.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.work.boot.pay.config.alipay.AlipayConfig;
import com.work.boot.pay.pojo.dto.AliPayResDTO;
import com.work.boot.pay.pojo.entity.Order;
import com.work.boot.pay.service.AliPayService;
import com.work.boot.pay.service.WxPayService;
import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.entity.Payuser;
import com.work.boot.pojo.query.PayuserQuery;
import com.work.boot.pojo.query.UserQuery;
import com.work.boot.service.PayuserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname payuserController
 * @Description TODO
 * @Date 2020/5/31 13:44
 * @CreateComputer by PC
 * @Created by cxd
 */
@RequestMapping("/payuser")
@Controller
public class PayuserController extends BaseController{

    @Resource
    private PayuserService payuserService;

    @Resource
    AliPayService aliPayService;
    @Resource
    WxPayService wxPayService;


    @RequestMapping("/payuserhtml")
    public String payuserhtml(){
        return "payuserlist";
    }

    @RequestMapping("/payuserpuls")
    public String payuserpuls(){
        return "payuserpuls";
    }

    @RequestMapping("/showpaydata")
    public String showpaydata(){
        return "showpaydata";
    }

    @RequestMapping("ajaxlist")
    @ResponseBody
    public PageDTO paylist(PayuserQuery payuserQuery){
        return payuserService.paylist(payuserQuery);
    }

    @RequestMapping("ajaxlistpro")
    @ResponseBody
    public PageDTO ajaxlistpro(PayuserQuery payuserQuery){

        payuserQuery.setUid(getUserId());

        return payuserService.ajaxlistpro(payuserQuery);
    }


    @RequestMapping("ajaxrecharge")
    @ResponseBody
    public PageDTO ajaxrecharge(UserQuery userQuery){

        return payuserService.ajaxrecharge(userQuery);
    }

    @RequestMapping("/datebing")
    @ResponseBody
    public ResponseDTO datebing( ){

        return payuserService.datebing();
    }

    @RequestMapping("/paymoney")
    public String paymoney(Integer userpayid,String payType, HttpServletRequest request, Model model) throws Exception {

        Payuser p = payuserService.getpayUser(userpayid);
        Order od = new Order();
        od.setPayType(payType);
        od.setActuallyPaid(p.getPaymoney());
        od.setCreateTime(new Date());
        od.setSubject("DD物业收费");// 设置一个描述
        od.setOrderId(Long.valueOf(p.getUserpayid()));
        od.setActuallyPaid(new BigDecimal("0.01")); // 测试支付金额


        if ("支付宝".equals(payType)) {
            ResponseDTO responseDTO = aliPayService.unifiedPay(od);
            // 由于微信付款是返回微信的二维码地址,所以这里为了照顾微信,我们不能用@ResponseBody
            request.setAttribute("form", responseDTO.getObject(String.class));
            return "forward:/payuser/aliPay";
        } else if ("微信".equals(payType)) {// 微信支付,
            ResponseDTO responseDTO = wxPayService.unifiedPay(od);
            // 如果是手机,直接重定向到地址

            model.addAllAttributes((Map<String, ?>) responseDTO.getData());
            return "pages/back/pay/wx-saomaPay";

            // 如果是电脑,那么就跳转到微信扫码付款页面去
        }

        return "payuserlist";
    }

    @RequestMapping("/aliPay")
    @ResponseBody
    String aliPay(HttpServletRequest request) {
        return (String) request.getAttribute("form");
    }

    @RequestMapping("/payali")
    String payali(Model model,Long userpayid) {
        model.addAttribute("userpayid",userpayid);
        return "payali";
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseDTO add(Payuser payuser) {

        return payuserService.add(payuser);
    }
    // 支付宝的通知的异步接口
    @RequestMapping("/aliPayResYb")
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
                    payuser.setUserpayid(Integer.valueOf(Math.toIntExact( aliPayRes.getOut_trade_no())));
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

    // 支付宝的通知的同步接口
    @RequestMapping("/aliPayResTb")
    String aliPayResTb(HttpServletRequest request, AliPayResDTO aliPayRes) {
        getUserId();
        System.out.println(getUserId());
        return "forward:/home/main";
    }

}
