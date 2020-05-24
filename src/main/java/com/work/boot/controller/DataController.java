package com.work.boot.controller;


import com.work.boot.pojo.dto.RData;
import com.work.boot.pojo.dto.ResultData;
import com.work.boot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * create:XXXXX
 * date:2020/5/21
 */
@Controller
@RequestMapping("/date")
public class DataController {

    @Resource
    private UserService userService ;
    @RequestMapping("/showdate")
    public String showadmin() {
        return "getcitydemo";
    }

    @RequestMapping("/getcity")
    @ResponseBody
    public ResultData getcity(){

        ResultData resultData = new ResultData();

        List<RData> list = new ArrayList();

        RData rData = new RData();
        rData.setName("视频广告");
        rData.setValue(1);

        RData aData = new RData();
        aData.setName("视频广告");
        aData.setValue(2);

        RData bData = new RData();
        bData.setName("电视广告");
        bData.setValue(3);

        RData cData = new RData();
        cData.setName("电梯广告");
        cData.setValue(4);


        list.add(aData);
        list.add(bData);
        list.add(cData);
        list.add(rData);

        resultData.setList(list);

        return resultData;





    }


    @RequestMapping("/test")
    @ResponseBody
    public ResultData test(){

        ResultData resultDatas  = userService.datatest();

        ResultData resultData = new ResultData();

        List<RData> list = new ArrayList();

        RData rData = new RData();
        rData.setName("视频广告");
        rData.setValue(200);

        RData aData = new RData();
        aData.setName("视频广告");
        aData.setValue(200);

        RData bData = new RData();
        bData.setName("电视广告");
        bData.setValue(200);

        RData cData = new RData();
        cData.setName("电梯广告");
        cData.setValue(300);


        list.add(aData);
        list.add(bData);
        list.add(cData);
        list.add(rData);

        resultData.setList(list);

        return resultDatas;
    }

}
