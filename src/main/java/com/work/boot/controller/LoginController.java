package com.work.boot.controller;

import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.query.UserQuery;
import com.work.boot.pojo.query.UserQueryS;
import com.work.boot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Classname LoginController
 * @Description TODO
 * @Date 2020/6/3 0:01
 * @CreateComputer by PC
 * @Created by cxd
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    private UserService userService;


    @RequestMapping("/logins")

    @ResponseBody
    public ResponseDTO login(UserQueryS user) {// 这个方法是执行登录操作的
        // 获取subject
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUphoneid(),user.getUpassword());
        Subject subject = SecurityUtils.getSubject();

        subject.getSession().setAttribute("code", user.getCode());

        try {
            subject.login(usernamePasswordToken);
        }catch (Exception e){
            return ResponseDTO.fail("defult", 500);
        }
        return ResponseDTO.ok("success", 200);

    }

    @RequestMapping("/readduser")
    public String readduser() {

        return "readduser";

    }

    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO add(User user, MultipartFile ruimg) {


        String imgname = saveFile(ruimg, "/upload/user/");
        user.setUimg(imgname);
        return userService.add(user);


    }

}
