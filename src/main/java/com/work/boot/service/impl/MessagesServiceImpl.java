package com.work.boot.service.impl;


import com.work.boot.dao.AdminDao;
import com.work.boot.dao.MessageDao;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Message;
import com.work.boot.pojo.entity.User;
import com.work.boot.service.MessagesService;
import com.work.boot.util.GetLimit;
import com.work.boot.util.GetUUID32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {

    @Resource
    private MessageDao messageDao;

    @Resource
    private AdminDao adminDao;

    @Override
    @Transactional
    public Result delmessages(String... ids) {
        //权限判断
        Result result = new Result();
        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            for (String id : ids) {
                int i = messageDao.deleteByPrimaryKey(id);
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
    public Result getmessagesAll(Integer page, Integer limit) {
        Integer sta = (page - 1) * limit;
        List<Message> list = messageDao.selectAlllimet(sta, limit);

        if (list != null && list.size() > 0) {
            aidtoaname(list);
        }

        Result result = new Result();

        result.setStatus(0);
        result.setItem(list);
        result.setTotal(messageDao.getCount());
        return result;


    }

    @Override
    public Result selectByLikemessages(String username, String useruid, Integer itype, Integer page, Integer limit) {
        Result result = new Result();
        List<Message> list =messageDao.selectByLikemessages(username, useruid, itype, GetLimit.getPage(page, limit), limit);

        list = aidtoaname(list);
        result.setStatus(0);
        result.setItem(list);
        result.setTotal(list.size());
        return result;
    }

    @Override
    public Result addMessages(Message nMessage, HttpServletRequest request) {
        Result result = new Result();


        if (nMessage.getIname() == null || "".equals(nMessage.getIname())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (nMessage.getIcontent() == null || "".equals(nMessage.getIcontent())) {
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
        nMessage.setIid(id);


        nMessage.setAcid(u.getUid());
        nMessage.setXid(u.getXid());

//
//        MultimediaInfo info = VideoUtil.getInfo(video.getVideoPath(), request);
//        Float f =  Float.valueOf(info.getDuration()/1000);

        //"xx.mp4"
//        user.setUimg("img/"+user.getUimg());
        //因为时添加操作  需要事务  设置手动回滚
//        admin.setApassword(Md5Util.encryption(admin.getApassword(), admin.getAname()));
        try {

            int i =messageDao.insert(nMessage);
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
    public Message selectById(String iid) {

        Message nMessage =messageDao.selectById(iid);
        return nMessage;

    }

    @Override
    @Transactional
    public Result updateMessage(Message nMessage) {
        Result result = new Result();
        nMessage.setIupdatedate(new Date());
        try {
            int i =messageDao.updateByPrimaryKeySelective(nMessage);
            if (i > 0) {
                result.setMessage("成功!");
                result.setStatus(200);
            } else {
                result.setStatus(500);
                result.setMessage("插入失败!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();


        }

        return result;
    }


    // aid 转换为 aname 方法
    private List<Message> aidtoaname(List<Message> list) {

        list.forEach(v -> {
            String aname = adminDao.selectanameById(v.getAcid());
            v.setAcid(aname);
            String uname = adminDao.selectanameById(v.getAuid());
            v.setAuid(uname);

        });
        return list;

    }
}
