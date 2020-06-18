package com.work.boot.pay.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * creator：cxd
 * date: 2020/6/12
 */
public class DeviceUtil {
    // 判断来者是否是手机
    public static boolean isMobile() {
        String userAgent = getRequest().getHeader("user-agent");
        if(userAgent!=null){
            if (userAgent.contains("Android") || userAgent.contains("iPhone")) {
                return true;
            }
        }

        return false;
    }


    // 判断来者是否是微信
    public static boolean isWx() {
        Enumeration<String> headerNames = getRequest().getHeaderNames();
        String userAgent = getRequest().getHeader("user-agent");
        if(userAgent!=null){
            if (userAgent.contains("MicroMessenger")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取HttpServletRequest
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取HttpSession对象
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    // 判断来者是否是手机
    public static boolean isAjax() {
        return "XMLHttpRequest".equals(getRequest().getHeader("X-Requested-With"));
    }
}
