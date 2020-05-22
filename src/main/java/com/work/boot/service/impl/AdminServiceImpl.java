package com.work.boot.service.impl;


import com.work.boot.dao.AdminDao;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Admin;
import com.work.boot.pojo.entity.User;
import com.work.boot.service.AdminService;
import com.work.boot.util.GetLimit;
import com.work.boot.util.GetUUID32;
import com.work.boot.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {


    @Resource
    private AdminDao adminDao;


    @Override
    public Admin checkAdmin(String aname, String apassword) {

        Admin admin = adminDao.checkAdmin(aname, apassword);

        return admin;
    }

    @Override
    public Result getadmin(Integer page, Integer limit) {
        Integer sta = (page - 1) * limit;
        //需要去数据库获取数据,返回对应的信息
        //用户的id需要转换成用户的名字
        //判断当前用户是管理员还是普通用户--->在讲解登录模块的时候来分析权限
        //查所有的视频(包括了上线的视频和下线的视频)
        List<Admin> list = adminDao.getadmin(sta, limit);
        //通过遍历list 获取用户的id,使用用户的id获取对应用户的名字,将名字赋值给当前的video


//        list = aidtoaname(list);

        Result result = new Result();

        result.setStatus(0);
        result.setItem(list);
        result.setTotal(adminDao.getCount());
        return result;

    }

    @Override
    public Result addAdmin(Admin admin, HttpServletRequest request) {
        Result result = new Result();


        if (admin.getAphoneid() == null || "".equals(admin.getAphoneid())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (admin.getApassword() == null || "".equals(admin.getApassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }

        if (admin.getApassword() == null || "".equals(admin.getApassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }

        HttpSession session = request.getSession();
//        User u = (User)request.getSession().getAttribute("user");
        // 视频的地址
        // 视频的描述
        // 视频的封面
        String id = GetUUID32.getuuid32();

        Date date = new Date();
        admin.setAid(id);


//        user.setAid(u.getUid());

//
//        MultimediaInfo info = VideoUtil.getInfo(video.getVideoPath(), request);
//        Float f =  Float.valueOf(info.getDuration()/1000);

        //"xx.mp4"
//        user.setUimg("img/"+user.getUimg());
        //因为时添加操作  需要事务  设置手动回滚
        admin.setApassword(Md5Util.encryption(admin.getApassword(), admin.getAname()));
        try {

            int i = adminDao.insert(admin);
            if (i > 0) {
                result.setMessage("成功!");
                result.setStatus(200);
            } else {
                result.setStatus(500);
                result.setMessage("插入失败!");
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
    }

    @Override
    public Result getLikeUsername(String username, String useruid, String userphone, Integer page, Integer limit) {

        Result result = new Result();
        List<User> list = adminDao.getLikeAdmin(username, useruid, userphone, GetLimit.getPage(page, limit), limit);

//        list = aidtoaname(list);
        result.setStatus(0);
        result.setItem(list);
        result.setTotal(list.size());
        return result;
    }

    @Override
    public Result delAdminById(String... ids) {
        //权限判断
        Result result = new Result();
        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            for (String id : ids) {
                int i = adminDao.deleteByPrimaryKey(id);
                if (i == 0) {
                    return result;
                }
            }
            result.setStatus(200);
            result.setMessage("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }

        return result;
    }

    @Override
    public Admin selectById(String aid) {


        Admin admin = adminDao.selectById(aid);
        return admin;
    }

    @Override
    public Result toedituser(Admin admin, HttpServletRequest request) {

        Result result = new Result();


        if (admin.getAphoneid() == null || "".equals(admin.getAphoneid())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (admin.getApassword() == null || "".equals(admin.getApassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }


        // 视频的地址
        // 视频的描述
        // 视频的封面
        String id = admin.getAid();

        Date date = new Date();
        admin.setAid(id);


//
//        MultimediaInfo info = VideoUtil.getInfo(video.getVideoPath(), request);
//        Float f =  Float.valueOf(info.getDuration()/1000);

        //"xx.mp4"

        if (!admin.getApassword().equals(admin.getApassword())) {
            admin.setApassword(Md5Util.encryption(admin.getApassword(), admin.getAname()));
        }
        try {

            adminDao.deleteByPrimaryKey(admin.getAid());
            int i = adminDao.insert(admin);
            if (i > 0) {
                result.setMessage("成功!");
                result.setStatus(200);
            } else {
                result.setStatus(500);
                result.setMessage("插入失败!");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }


    }

    @Override
    public Result updataAdmin(String aid, String field, String value) {
        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            adminDao.updateByuid(aid, field, value);
            result.setStatus(200);
            result.setMessage("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }

        return result;
    }


}
