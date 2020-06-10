package com.work.boot.controller;

import com.work.boot.pojo.dto.*;
import com.work.boot.pojo.entity.Maintenanceuser;
import com.work.boot.pojo.entity.User;
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

    @RequestMapping("/addmaintenanceuser")
    String addmaintenanceuser(){
        return "addmaintenanceuser";
    }


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

    @RequestMapping("/add")
    @ResponseBody
    ResponseDTO add(Maintenanceuser maintenanceuser) {

        return maintenanceuserService.add(maintenanceuser);
    }


    @RequestMapping("/delete")
    @ResponseBody
    ResponseDTO delete(Maintenanceuser maintenanceuser) {

        return maintenanceuserService.delete(maintenanceuser);
    }


    @RequestMapping("/edit")
    @ResponseBody
    ResponseDTO edit(Maintenanceuser maintenanceuser) {

        return maintenanceuserService.edit(maintenanceuser);
    }



}
