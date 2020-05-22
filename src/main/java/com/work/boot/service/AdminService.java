package com.work.boot.service;


import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    Admin checkAdmin(String username, String password);

    Result getadmin(Integer page, Integer limit);

    Result addAdmin(Admin admin, HttpServletRequest request);

    Result getLikeUsername(String username, String useruid, String userphone, Integer page, Integer limit);

    Result delAdminById(String... ids);

    Admin selectById(String aid);

    Result toedituser(Admin admin, HttpServletRequest request);


    Result updataAdmin(String aid, String field, String value);
}
