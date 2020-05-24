package com.work.boot.service.impl;

import com.work.boot.dao.AdminDao;
import com.work.boot.dao.UserDao;
import com.work.boot.pojo.dto.RData;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.dto.ResultData;
import com.work.boot.pojo.entity.Admin;
import com.work.boot.pojo.entity.User;
import com.work.boot.service.UserService;
import com.work.boot.util.GetLimit;
import com.work.boot.util.GetUUID32;
import com.work.boot.util.Md5Util;
import com.work.boot.util.UpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private AdminDao adminDao;

    @Override
    public Result getAll() {

        List<User> list = userDao.selectAll();

        Result result = new Result();

        result.setStatus(0);
        result.setItem(list);
        result.setTotal(list.size());
        return result;

    }

    @Override
    public Result getusers(Integer page, Integer limit) {
        Integer sta = (page - 1) * limit;
        //需要去数据库获取数据,返回对应的信息
        //用户的id需要转换成用户的名字
        //判断当前用户是管理员还是普通用户--->在讲解登录模块的时候来分析权限
        //查所有的视频(包括了上线的视频和下线的视频)
        List<User> list = userDao.getusers(sta, limit);
        //通过遍历list 获取用户的id,使用用户的id获取对应用户的名字,将名字赋值给当前的video
//
//        list.forEach( v -> {
//            String aname = adminMapper.selectanameById(v.getAid());
//            v.setAid(aname);
//        });

        list = aidtoaname(list);

        Result result = new Result();

        result.setStatus(0);
        result.setItem(list);
        result.setTotal(userDao.getCount());
        return result;

    }

    @Override
    public Result seeuser(String uid) {

        User user = userDao.selectByPrimaryKey(uid);
        Result result = new Result();
        List<User> list = new ArrayList();
        list.add(user);
        result.setStatus(0);
        result.setItem(list);
        result.setTotal(1);
        return result;

    }


    //密码校验
    @Override
    public User checkUser(String uname, String upassword) {


        User user = userDao.selectcheckUser(uname, upassword);

        return user;

    }

    @Override

    public Result adduimg(MultipartFile uimg, HttpServletRequest request) {

        Result result = new Result();
        //此处需要将这个文件上传项目中
        try {
            UpUtils.upfile(uimg, request);
            result.setMessage(uimg.getOriginalFilename());
            result.setStatus(200);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(500);
            result.setMessage("网路异常!");
            return result;

        }

    }

    @Override
    @Transactional
    public Result addUser(User user, HttpServletRequest request) {
        Result result = new Result();

        if (user.getUimg() == null || "".equals(user.getUimg())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUphoneid() == null || "".equals(user.getUphoneid())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUpassword() == null || "".equals(user.getUpassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUbroom() == null || "".equals(user.getUbroom())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUpassword() == null || "".equals(user.getUpassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }

        HttpSession session = request.getSession();
        User u = (User) request.getSession().getAttribute("user");
        // 视频的地址
        // 视频的描述
        // 视频的封面
        String id = GetUUID32.getuuid32();

        Date date = new Date();
        user.setUid(id);


//        user.setAid(u.getUid());

//
//        MultimediaInfo info = VideoUtil.getInfo(video.getVideoPath(), request);
//        Float f =  Float.valueOf(info.getDuration()/1000);

        //"xx.mp4"
        user.setUimg("img/" + user.getUimg());
        //因为时添加操作  需要事务  设置手动回滚
        user.setUpassword(Md5Util.encryption(user.getUpassword(), user.getUname()));
        try {

            int i = userDao.insert(user);

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
    public User selectById(String uid) {
        User user = userDao.selectById(uid);
        return user;
    }

    @Override
    public Result toedituser(User user, HttpServletRequest request) {

        Result result = new Result();

        if (user.getUimg() == null || "".equals(user.getUimg())) {
            result.setMessage("填写错误!");
            result.setStatus(200);
            return result;
        }
        if (user.getUphoneid() == null || "".equals(user.getUphoneid())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUpassword() == null || "".equals(user.getUpassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUbroom() == null || "".equals(user.getUbroom())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUpassword() == null || "".equals(user.getUpassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }

        HttpSession session = request.getSession();
        User u = (User) request.getSession().getAttribute("user");
        // 视频的地址
        // 视频的描述
        // 视频的封面
        String id = user.getUid();


        user.setUid(id);


        if (u != null) {
            user.setAid(u.getUid());
        }


        user.setUimg("img/" + user.getUimg().substring(4));

        User userp = userDao.selectByPrimaryKey(user.getUid());


        if (!userp.getUpassword().equals(user.getUpassword())) {

            user.setUpassword(Md5Util.encryption(user.getUpassword(), user.getUname()));

        }
        try {
            userDao.deleteByPrimaryKey(user.getUid());
            int i = userDao.insert(user);
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
    public ResultData datatest() {
        ResultData resultData = new ResultData();
        List<RData> list = new ArrayList<>();




        Integer count = userDao.getCount();

        List<User> users = userDao.selectAll();

        int sum = 0;

        for (User user : users) {
            RData rData = new RData();
            rData.setName(user.getUid());
            rData.setValue(user.getCstate());
            list.add(rData);
        }

        users.forEach( user -> {
            RData rData = new RData();
            rData.setName(user.getUid());
            rData.setValue(user.getCstate());
            list.add(rData);
        });


        resultData.setList(list);

        return  resultData;

    }


    @Override
    @Transactional
    public Result delUserById(String... ids) {
        //权限判断
        Result result = new Result();
        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            for (String id : ids) {
                int i = userDao.deleteByPrimaryKey(id);
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
    @Transactional
    public Result updataUser(String uid, String field, String value) {
        //权限判断
        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            userDao.updateByuid(uid, field, value);
            result.setStatus(200);
            result.setMessage("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }

        return result;
    }

    // 模糊多条件查询
    @Override
    public Result getLikeUsername(String username, String useruid, String userphone, Integer page, Integer limit) {
        Result result = new Result();
        List<User> list = userDao.getLikeUsername(username, useruid, userphone, GetLimit.getPage(page, limit), limit);

        list = aidtoaname(list);
        result.setStatus(0);
        result.setItem(list);
        result.setTotal(list.size());
        return result;
    }


    // aid 转换为 aname 方法
    private List<User> aidtoaname(List<User> list) {

        list.forEach(v -> {
            String aname = adminDao.selectanameById(v.getAid());
            v.setAid(aname);
        });
        return list;

    }


}
