package com.work.boot.controller;

import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.entity.Payment;
import com.work.boot.pojo.query.PaymentQuery;
import com.work.boot.pojo.query.PayuserQuery;
import com.work.boot.service.PayuserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Classname payuserController
 * @Description TODO
 * @Date 2020/5/31 13:44
 * @CreateComputer by PC
 * @Created by cxd
 */
@RequestMapping("/payment")
@Controller
public class PaymentController {

    @Resource
    private PayuserService payuserService;

    @RequestMapping("/setpay")
    public String setpay(){
        return "setpay";
    }

    @RequestMapping("ajaxlist")
    @ResponseBody
    public PageDTO paylist(PayuserQuery payuserQuery){

        return payuserService.paylist(payuserQuery);
    }

    @RequestMapping("ajaxpayment")
    @ResponseBody
    public ResponseDTO ajaxpayment(){

        return payuserService.ajaxpayment();
    }

    @RequestMapping("ajaxgetmoney")
    @ResponseBody
    public ResponseDTO ajaxgetmoney(Integer id){

        return payuserService.ajaxgetmoney(id);
    }

    @RequestMapping("ajaxeditpayment")
    @ResponseBody
    public ResponseDTO ajaxeditpayment(PaymentQuery paymentQuery){

        return payuserService.ajaxeditpayment(paymentQuery);
    }
    @RequestMapping("/addpayuser")
    public String addpayuser(){
        return "addrecharge";
    }
}
