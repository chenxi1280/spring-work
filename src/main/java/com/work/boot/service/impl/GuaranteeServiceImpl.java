package com.work.boot.service.impl;

import com.work.boot.dao.GuaranteeDao;
import com.work.boot.dao.MaintenanceuserDao;
import com.work.boot.dao.UserDao;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.pojo.entity.Maintenanceuser;
import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.query.UserQuery;
import com.work.boot.pojo.vo.GuaranteeAllVo;
import com.work.boot.service.GuaranteeService;
import com.work.boot.util.GetUUID32;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class GuaranteeServiceImpl implements GuaranteeService {
    @Resource
    private GuaranteeDao guaranteeDao;
    @Resource
    private MaintenanceuserDao maintenanceuserDao;
    @Resource
    private UserDao userDao;

    @Override
    public Result getguarantees(Integer page, Integer limit) {

        Integer sta = (page - 1) * limit;
        List<GuaranteeAllVo> list = guaranteeDao.selectAlllimet(sta, limit);



        //循环 设置名字
        list.forEach( g -> {
            User user = userDao.selectByPrimaryKey(g.getUid());
            g.setUphone(user.getUphoneid());
            g.setUUsername(user.getUusername());
            if (g.getMaintenanceuserid()!=null) {
                // 查询 维修人员表中的 维修人员名字 并 传给 返回前端的 对象
                g.setMaintenanceusername(maintenanceuserDao.selectByPrimaryKey(g.getMaintenanceuserid()).getMaintenanceusername());
            }

        } );

//        List<GuaranteeAllVo> guaranteeAllQuries = maintenanceuserDao.selectNameByList(list);

//        Map<Integer, List<GuaranteeAllVo>> collect = guaranteeAllQuries.stream().collect(Collectors.groupingBy(GuaranteeAllVo::getMaintenanceuserid));

//        for (GuaranteeAllVo guarantee : list) {
//
//            for (GuaranteeAllVo guaranteeAllQury : guaranteeAllQuries) {

//            }
//            guarantee.setMaintenanceusername();
//        }


//        if (list != null && list.size() > 0) {
//            aidtoaname(list);
//        }

        Result result = new Result();

        result.setStatus(0);
        result.setItem(list);
        result.setTotal(guaranteeDao.getCount());
        return result;
    }



    @Override
    public GuaranteeAllVo getguarantee(String rid) {


        GuaranteeAllVo guarantee = guaranteeDao.selectByid(rid);


        User user = userDao.selectByPrimaryKey(guarantee.getUid());
        guarantee.setUphone(user.getUphoneid());
        guarantee.setUUsername(user.getUusername());
        if (guarantee.getMaintenanceuserid()!=null) {
            guarantee.setMaintenanceusername(maintenanceuserDao.selectByPrimaryKey(guarantee.getMaintenanceuserid()).getMaintenanceusername());
        }


            return guarantee;
    }

    @Override
    @Transactional
    public Result chengeguarantee(String rid) {
        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            List<Maintenanceuser> list = maintenanceuserDao.selectOnin();
            ArrayList arrayList = new ArrayList();

            list.forEach(v -> {
                Integer maintenanceuseridstate = v.getMaintenanceuseridstate();
                if (maintenanceuseridstate > 1) {
                    arrayList.add(maintenanceuseridstate);
                }
            });
            int i = new Random().nextInt(list.size()) + 1;

            Guarantee guarantee = new Guarantee();
            guarantee.setMaintenanceuserid(i);
            guarantee.setRid(rid);
            guarantee.setRaccpetdate(new Date());

            int me = guaranteeDao.updateByPrimaryKeySelective(guarantee);
            int sta = guaranteeDao.updataState(rid);

            if (me == 1 && sta == 1) {
                result.setStatus(200);
                result.setMessage("操作成功");

            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return result ;
    }

    @Override
    @Transactional
    //完成
    public Result completeguarantee(String rid) {
        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        try {

            GuaranteeAllVo guaranteeAllVo = guaranteeDao.selectByid(rid);
            guaranteeAllVo.setRcompletiondate(new Date());
            guaranteeAllVo.setRstate(3);
            int sta = guaranteeDao.updateByPrimaryKeySelective(guaranteeAllVo);

            if (sta == 1) {
                result.setStatus(200);
                result.setMessage("操作成功");

            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return result ;
    }



    @Override
    public Result toeditguarantee(Guarantee guarantee) {

        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            guarantee.setRcompletiondate(new Date());
            int sta = guaranteeDao.updateByPrimaryKeySelective(guarantee);

            if (sta == 1) {
                result.setStatus(200);
                result.setMessage("操作成功");

            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return result ;
    }

    @Override
    //模糊查询
    public Result selectByLikeguarantee(String uphone, String username, Integer rstate, String maintenanceusername, Integer page, Integer limit) {


        Integer sta = (page - 1) * limit;
        List<UserQuery> list =  userDao.selectLikeUsername(username,uphone);

        List<GuaranteeAllVo> lists = guaranteeDao.selectByUid(list,rstate,sta,limit);


        //循环 设置名字
        lists.forEach( g -> {
            User user = userDao.selectByPrimaryKey(g.getUid());
            g.setUphone(user.getUphoneid());
            g.setUUsername(user.getUusername());
            if (g.getMaintenanceuserid()!=null) {
                g.setMaintenanceusername(maintenanceuserDao.selectByPrimaryKey(g.getMaintenanceuserid()).getMaintenanceusername());
            }

        } );


        Result result = new Result();

        result.setStatus(0);
        result.setItem(lists);
        result.setTotal(guaranteeDao.getCount());
        return result;

    }

    @Override
    public Result ajaxaddguarantee(GuaranteeAllVo guaranteeAllVo, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        guaranteeAllVo.setRid(GetUUID32.getuuid32());
        guaranteeAllVo.setUid(user.getUid());
        guaranteeAllVo.setUphone(user.getUphoneid());
        guaranteeAllVo.setRstate(1);
        guaranteeAllVo.setRpublicdate(new Date());

        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        try {

            int sta  = guaranteeDao.insertSelective(guaranteeAllVo);

            if (sta == 1) {
                result.setStatus(200);
                result.setMessage("操作成功");

            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return result ;
    }

    @Override
    public Result getmyguarateelist(HttpServletRequest request, Integer page, Integer limit) {

        Integer sta = (page - 1) * limit;

        HttpSession session = request.getSession();
        User users = (User) session.getAttribute("user");
        Result result = new Result();
        result.setTotal(0);

        try{
            List<GuaranteeAllVo> lists = guaranteeDao.selectLikeUserAll(users.getUid(),sta,limit);
            //循环 设置名字
            lists.forEach(g -> {
                User user = userDao.selectByPrimaryKey(g.getUid());
                g.setUphone(user.getUphoneid());
                g.setUUsername(user.getUusername());
                if (g.getMaintenanceuserid() != null) {
                    g.setMaintenanceusername(maintenanceuserDao.selectByPrimaryKey(g.getMaintenanceuserid()).getMaintenanceusername());
                }
                result.setTotal(guaranteeDao.getCount());
                result.setItem(lists);
            });
        }catch (NullPointerException e){
            e.printStackTrace();
            result.setTotal(0);
        }



        result.setStatus(0);


        return result;

    }

    @Override
    public Result delguarantee(String rid) {

        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            int sta = guaranteeDao.deleteByPrimaryKey(rid);

            if (sta == 1) {
                result.setStatus(200);
                result.setMessage("操作成功");

            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return result ;
    }

}
