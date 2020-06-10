package com.work.boot.controller;

import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.RData;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.dto.ResultData;
import com.work.boot.pojo.query.MaintenanceuserQuery;
import com.work.boot.service.MaintenanceuserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Classname Maintenanceuser
 * @Description TODO
 * @Date 2020/5/27 13:12
 * @CreateComputer by PC
 * @Created by cxd
 */
@Controller
@RequestMapping("/maintenanceuser")
public class MaintenanceuserController {
    @Resource
    private MaintenanceuserService maintenanceuserService;

    @RequestMapping("/getmaintenanceuser")
    @ResponseBody
    public Result getmaintenanceuser(){

        return maintenanceuserService.getmaintenanceuser();
    }

    @RequestMapping("/ajaxList")
    @ResponseBody
    public PageDTO ajaxList(MaintenanceuserQuery maintenanceuserQuery){

        return maintenanceuserService.ajaxList(maintenanceuserQuery);
    }

}
