package com.work.boot.controller;

import com.work.boot.pojo.dto.Result;
import com.work.boot.service.GuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("/guarantee")
@Controller
public class GuaranteeController {

    @Resource
    private GuaranteeService guaranteeService;

    @RequestMapping("/getguarantee")
    public String getguarantee() {

        return "showguarantee";
    }

    @RequestMapping("/getguarantees")
    @ResponseBody
    public Result getguarantees(Integer page, Integer limit) {

        Result result = guaranteeService.getguarantees(page, limit);
        return result;
    }

}