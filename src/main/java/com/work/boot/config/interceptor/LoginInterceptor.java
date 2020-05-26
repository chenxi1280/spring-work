package com.work.boot.config.interceptor;

import com.work.boot.pojo.entity.User;
import com.work.boot.service.AdminService;
import com.work.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Configuration

public class LoginInterceptor implements HandlerInterceptor   {

    @Resource
    private UserService userService;
    @Resource
    private AdminService adminService;



    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // 执行完毕，返回前拦截
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // 在处理过程中，执行拦截
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        // 在拦截点执行前拦截，如果返回true则不执行拦截点后的操作（拦截成功）
        // 返回false则不执行拦截
        HttpSession session = request.getSession();
        String url = request.getRequestURI(); // 获取登录的uri，这个是不进行拦截的
        //if(session.getAttribute("_CURRENT_USER")!=null || url.indexOf("home.action")!=-1 || url.indexOf("login.action")!=-1) {

        User user = (User) session.getAttribute("user");

        if (user != null) {
            // 登录成功不拦截
            //这里面是user不为null的情况
            session.setAttribute("user", user);
            //重新设置过期时间
            session.setMaxInactiveInterval(60 * 60 * 24);

            return true;

        } else {
            // 拦截后进入登录页面
            Cookie[] cookies = request.getCookies();
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

//                    //在到数据库中校验用户名或者密码是否错误, 因为用户可能换浏览器或者机器，修改了密码。
//
//                    User u = userService.checkUser(username, password);
//
//                    // 表示用户的信息校验成功
//                    if(null != user) {
//                        // 根据cookie新又重新登录了一次，需要重新设置session
                    session.setAttribute("user", user);
                    session.setMaxInactiveInterval(60 * 60 * 24);
                    response.sendRedirect(request.getContextPath() + "/home/setCookie");

                    return true;
//                    }else {  //用户名或者密码错误了
//                        u = AdmintoUser.getAdminToUser(adminService.checkAdmin(username,password));
//
//                        if (u != null){
//
//                            // 根据cookie新又重新登录了一次，需要重新设置session
//                            session.setAttribute("user", user);
//                            session.setMaxInactiveInterval(60*60*24);
//
//                            return true;
//                        }
//
//                        response.sendRedirect(request.getContextPath()+"/home/loginh");
//                        return false;
//                    }
                } else { // cookie中没有存储用户名和密码
                    response.sendRedirect(request.getContextPath() + "/home/loginh");
                    return false;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/home/loginh");
                return false;
            }

//            response.sendRedirect(request.getContextPath()+"/home/loginh");
//
//            return false;
        }
    }
}