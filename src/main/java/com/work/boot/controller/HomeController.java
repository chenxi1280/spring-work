package com.work.boot.controller;

import cn.dsna.util.images.ValidateCode;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.User;
import com.work.boot.service.AdminService;
import com.work.boot.service.LoginService;
import com.work.boot.service.UserService;
import com.work.boot.util.AdmintoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/home", name = "HomeController")
public class HomeController {

    @Resource
    private LoginService loginService;
    @Resource
    private UserService userService;
    @Resource
    private AdminService adminService;


    @RequestMapping("/index")
    public String index(HttpServletRequest request) {


        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();

        if (null != cookies) {
            String username = null;
            String password = null;
            for (Cookie cookie : cookies) {
                // 获取cookie的名字，因为一个网站存在很多的cookie
                String name = cookie.getName();
                //如果是用户名对应的cookie, 那么说白了用户在登录的时候，勾选了 "记住我" 这个checkbox,
                // 会在 LoginSerlvet中执行：
                //           Cookie usernameCookie = new Cookie("username", username);
                //           resp.addCookie(usernameCookie);
                if ("username".equals(name)) {
                    username = cookie.getValue();
                } else if ("password".equals(name)) {
                    password = cookie.getValue();
                }
            }

            // TODO 如果用户名和密码不为空，就需要到数据库查询
            if (null != username && null != password) {

                User u = userService.checkUser(username, password);

                // 表示用户的信息校验成功
                if (null != u) {
                    // 根据cookie新又重新登录了一次，需要重新设置session
                    session.setAttribute("user", u);
                    session.setMaxInactiveInterval(60 * 60 * 24);
//                        response.sendRedirect(request.getContextPath()+"/home/index");
                    return "index";
                } else {  //用户名或者密码错误了
                    u = AdmintoUser.getAdminToUser(adminService.checkAdmin(username, password));

                    if (u != null) {

                        // 根据cookie新又重新登录了一次，需要重新设置session
                        session.setAttribute("user", u);
                        session.setMaxInactiveInterval(60 * 60 * 24);

                        return "index";
                    }

                    return "login";

                }

            }
        }
        return "login";

    }

    @RequestMapping("/loginh")
    public String loginh() {

        return "login";

    }

    @RequestMapping("/logintest")
    public String logintest() {

        return "login-1";

    }

    @RequestMapping("/setCookie")
    public String setCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        if (null != cookies) {
            String username = null;
            String password = null;
            for (Cookie cookie : cookies) {
                // 获取cookie的名字，因为一个网站存在很多的cookie
                String name = cookie.getName();
                //如果是用户名对应的cookie, 那么说白了用户在登录的时候，勾选了 "记住我" 这个checkbox,
                // 会在 LoginSerlvet中执行：
                //           Cookie usernameCookie = new Cookie("username", username);
                //           resp.addCookie(usernameCookie);
                if ("username".equals(name)) {
                    username = cookie.getValue();
                } else if ("password".equals(name)) {
                    password = cookie.getValue();
                }
            }

            // TODO 如果用户名和密码不为空，就需要到数据库查询
            if (null != username && null != password) {

                User u = userService.checkUser(username, password);

                // 表示用户的信息校验成功
                if (null != u) {
                    // 根据cookie新又重新登录了一次，需要重新设置session
                    session.setAttribute("user", u);
                    session.setMaxInactiveInterval(60 * 60 * 24);
//                        response.sendRedirect(request.getContextPath()+"/home/index");
                    return "index";
                } else {  //用户名或者密码错误了
                    u = AdmintoUser.getAdminToUser(adminService.checkAdmin(username, password));

                    if (u != null) {

                        // 根据cookie新又重新登录了一次，需要重新设置session
                        session.setAttribute("user", u);
                        session.setMaxInactiveInterval(60 * 60 * 24);

                        return "index";
                    }


                    return "login";

                }


            }
        }
        return "login";
    }


    @RequestMapping("/login")
    @ResponseBody
    public Result login(String username, String password, String code, String close, HttpServletRequest
            request, HttpServletResponse response) {
        Result result = loginService.login(username.trim(), password.trim(), code.trim(), close, request, response);
        return result;
    }


    @RequestMapping("/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {
        //声明验证码
        //参数列表:宽度 , 高度, 验证的字符数量, 干扰线的数量
        ValidateCode validateCode = new ValidateCode(120, 40, 5, 50);
        //需要将验证码信息放在后台(session-->key--value)并且返回给前端
        //session
        //   code-->123
        //   code-->456
        request.getSession().setAttribute("code", validateCode.getCode());
        //将验证码图片以流的方式返回给前端
        try {
            validateCode.write(response.getOutputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/loginout")
    public String loginout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        Cookie u = new Cookie("username", "");

        Cookie p = new Cookie("password", "");
        int expire = 0;  //保留一个月

        u.setMaxAge(expire);  //设置cookie在浏览器的保存时间为一个月
        p.setMaxAge(expire);

        response.addCookie(u);   //添加到响应中，就会自动的存储在浏览器中
        response.addCookie(p);

        //springmvc默认采用的是转发   就是一次请求
        //因为转发会导致在一次登录登录界面时出现404
        //设置成了重定向 -- 表示执行一个新的请求-->不受到视图解析器的影响
        return "redirect:loginh";
    }


//    @RequestMapping("/getTelCode")
//    @ResponseBody
//    public Result getTelCode(String tel,HttpServletRequest request){
//        //我们进行手机短信认证  需要一个手机号
//        String telCode = SendmsUtil.getTelCode(tel);
//        HttpSession session = request.getSession();
//        session.setAttribute("telCode",telCode);
//        //需要设置过期时间
//        session.setMaxInactiveInterval(60*5);
//        Result result = new Result();
//        result.setMessage("短息已发送成功!");
//        return result;
//    }


}