package com.work.boot.util;

import com.work.boot.pojo.entity.Admin;
import com.work.boot.pojo.entity.User;


public class AdmintoUser {

    public static User getAdminToUser(Admin admin) {
        User user = new User();
        if (admin != null) {
            user.setUid(admin.getAid());
            user.setUname(admin.getAname());
            user.setUusername(admin.getAdminname());
            user.setUpassword(admin.getApassword());
            user.setXid(admin.getXid());
            user.setUphoneid(admin.getAphoneid());
            user.setTypeid(admin.getTypeid());

        } else {
            user = null;
        }
        return user;
    }
}
