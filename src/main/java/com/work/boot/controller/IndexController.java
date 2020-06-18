package com.work.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname IndexController
 * @Description TODO
 * @Date 2020/6/17 0:16
 * @CreateComputer by PC
 * @Created by cxd
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String i() {
        return "loginPage";
    }
}
