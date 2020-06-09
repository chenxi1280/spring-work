package com.work.boot.controller;

import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.entity.Payuser;
import com.work.boot.pojo.query.PayuserQuery;
import com.work.boot.pojo.query.UserQuery;
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
@RequestMapping("/payuser")
@Controller
public class PayuserController {

    @Resource
    private PayuserService payuserService;

    @RequestMapping("/payuserhtml")
    public String payuserhtml(){
        return "payuserlist";
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


}
