package com.work.boot.service.impl;

import com.work.boot.dao.GuaranteeDao;
import com.work.boot.dao.MaintenanceuserDao;
import com.work.boot.dao.UserDao;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.pojo.entity.Maintenanceuser;
import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.query.GuaranteeAllQury;
import com.work.boot.pojo.vo.GuaranteeAllVo;
import com.work.boot.service.GuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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


        list.forEach( g -> {
            User user = userDao.selectByPrimaryKey(g.getUid());
            g.setUphone(user.getUphoneid());
            g.setUUsername(user.getUusername());
            if (g.getMaintenanceuserid()!=null) {
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
    public Result chengeguarantee(String rid) {
        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        List<Maintenanceuser> list = maintenanceuserDao.selectAll();
        ArrayList arrayList = new ArrayList();

        list.forEach( v -> {
            Integer maintenanceuseridstate = v.getMaintenanceuseridstate();
            if (maintenanceuseridstate>1){
                arrayList.add(maintenanceuseridstate);
            }
        });
        int i = new Random().nextInt(arrayList.size() + 1);

        Guarantee guarantee = new Guarantee();
        guarantee.setMaintenanceuserid(i);
        guarantee.setRid(rid);
        int me = guaranteeDao.updateByPrimaryKeySelective(guarantee);

        if (me == 1 ){
            result.setStatus(200);
            result.setMessage("操作成功");

        }

        return result ;
    }

}
