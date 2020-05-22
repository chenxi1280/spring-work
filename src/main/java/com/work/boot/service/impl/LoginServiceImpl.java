package com.work.boot.service.impl;

import com.work.boot.dao.AdminDao;
import com.work.boot.dao.UserDao;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Admin;
import com.work.boot.pojo.entity.User;
import com.work.boot.service.LoginService;
import com.work.boot.util.AdmintoUser;
import com.work.boot.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDao userDao;

    @Resource
    private AdminDao adminDao;


    @Override
    public Result login(String uname, String password, String code, String close, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        //判定验证码 1   后台的验证码是放在session里面的 code-->?
        String codeValue = (String) request.getSession().getAttribute("code");


        if (code.equalsIgnoreCase(codeValue)) {
            //判定用户名密码 2-->先用用户名取数据库判断-->在判断密码
            User user = userDao.selectByUserName(uname);
            //对user进行判断
            if (user == null || !user.getUpassword().equals(Md5Util.encryption(password, uname))) {

                Admin admin = adminDao.selectByAdminName(uname);
                user = AdmintoUser.getAdminToUser(admin);

                //执行失败的信息
                if (user == null || !user.getUpassword().equals(Md5Util.encryption(password, uname))) {
                    result.setStatus(500);
                    result.setMessage("用户名或者密码错误,如果没有账号请进行注册!");

                } else {
                    setUserSession(user, result, close, request, response);

                }

            } else {
//                result.setStatus(200);
//                //将登录成功的用户信息放在session中,方便后面做权限使用
//                HttpSession session = request.getSession();
//                session.setAttribute("user",user);
                setUserSession(user, result, close, request, response);

            }
        } else {
            //设置失败的信息
            result.setStatus(500);
            result.setMessage("验证码错误!");
        }
        return result;
    }


    private void setUserSession(User user, Result result, String close, HttpServletRequest request, HttpServletResponse response) {
        result.setStatus(200);
        //将登录成功的用户信息放在session中,方便后面做权限使用
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(60 * 60 * 24);


        if (close != null || !"".equals(close)) {
            Cookie u = new Cookie("username", user.getUname());

            Cookie p = new Cookie("password", user.getUpassword());
            int expire = 86400;  //保留一个月

            u.setMaxAge(expire);  //设置cookie在浏览器的保存时间为一个月
            p.setMaxAge(expire);

            response.addCookie(u);   //添加到响应中，就会自动的存储在浏览器中
            response.addCookie(p);

        }


    }
}
