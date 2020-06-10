package com.work.boot.service;

import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.dto.ResultData;
import com.work.boot.pojo.query.MaintenanceuserQuery;

/**
 * @Classname MaintenanceuserService
 * @Description TODO
 * @Date 2020/5/27 13:14
 * @CreateComputer by PC
 * @Created by cxd
 */
public interface MaintenanceuserService {


    Result getmaintenanceuser();


    PageDTO ajaxList(MaintenanceuserQuery maintenanceuserQuery);
}
