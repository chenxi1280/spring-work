package com.work.boot.service.impl;

import com.work.boot.dao.MaintenanceuserDao;
import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.dto.ResultData;
import com.work.boot.pojo.entity.Maintenanceuser;
import com.work.boot.pojo.query.MaintenanceuserQuery;
import com.work.boot.pojo.vo.MaintenanceuserVO;
import com.work.boot.service.MaintenanceuserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname MaintenanceuserServiceImpl
 * @Description TODO
 * @Date 2020/5/27 13:14
 * @CreateComputer by PC
 * @Created by cxd
 */
@Service
public class MaintenanceuserServiceImpl implements MaintenanceuserService {

    @Resource
    private MaintenanceuserDao maintenanceuserDao;
    @Override
    public Result getmaintenanceuser() {

        List<Maintenanceuser> maintenanceusers = maintenanceuserDao.selectAll();

        Result result= new Result();
        result.setItem(maintenanceusers);
        result.setStatus(200);

        return result;
    }

    @Override
    public PageDTO ajaxList(MaintenanceuserQuery maintenanceuserQuery) {

        List<MaintenanceuserVO> maintenanceusers = maintenanceuserDao.selectList(maintenanceuserQuery);
        Integer count = maintenanceuserDao.selectCountList(maintenanceuserQuery);

        return PageDTO.setPageData(count,maintenanceusers);
    }

    @Override
    public ResponseDTO add(Maintenanceuser maintenanceuser) {

        maintenanceuser.setMaintenanceuseridstate(2);

        return ResponseDTO.get(maintenanceuserDao.insertSelective(maintenanceuser) ==1 );
    }

    @Override
    public ResponseDTO delete(Maintenanceuser maintenanceuser) {


        return ResponseDTO.get(maintenanceuserDao.deleteByPrimaryKey(maintenanceuser.getMaintenanceuserid()) ==1 );
    }

    @Override
    public ResponseDTO edit(Maintenanceuser maintenanceuser) {



        return ResponseDTO.get(maintenanceuserDao.updateByPrimaryKeySelective(maintenanceuser) == 1 );
    }
}
