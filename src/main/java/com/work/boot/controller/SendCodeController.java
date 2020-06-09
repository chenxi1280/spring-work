package com.work.boot.controller;

import com.work.boot.config.aliduanxin.SmsUtil;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.service.UserService;
import com.work.boot.util.math.MathUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Classname SendCodeController
 * @Description TODO
 * @Date 2020/6/3 8:59
 * @CreateComputer by PC
 * @Created by cxd
 */
@Controller
public class SendCodeController extends BaseController{

    @Resource
    UserService userService;


    /**
     * 发送登录验证码,手机号必须存在
     *
     * @return
     */
    @RequestMapping("/sendLoginCode")
    @ResponseBody
    ResponseDTO sendLoginCode(String phone)  {//
        // 先生成一个6为随机数
        String code = MathUtil.getRandomStr(6);
        // 此时应该把这个验证发给阿里云，让阿里云给这个手机发送验证码
        // 发送验证码之前应该去验证手机号是否正确。
        // 应该去判断是否有这个手机号。
        if (userService.checkPhoneExist(phone)) {// 这个用户如果存在才发，不存在就不发了
            try {
                SmsUtil.sendSmsCode(phone, code, "SMS_185242830");// 发送验证码
                getSession().setAttribute("loginCode", code);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseDTO.fail("验证码发送失败");
            }
            return ResponseDTO.ok("验证码发送成功");
        }
        return ResponseDTO.fail("验证码发送失败");
    }
}