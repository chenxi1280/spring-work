package com.work.boot.dao;

import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.pojo.entity.Maintenanceuser;
import com.work.boot.pojo.query.GuaranteeAllQury;
import com.work.boot.pojo.vo.GuaranteeAllVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MaintenanceuserDao {
    int deleteByPrimaryKey(Integer maintenanceuserid);

    int insert(Maintenanceuser record);

    int insertSelective(Maintenanceuser record);

    Maintenanceuser selectByPrimaryKey(Integer maintenanceuserid);

    int updateByPrimaryKeySelective(Maintenanceuser record);

    int updateByPrimaryKey(Maintenanceuser record);

    List<GuaranteeAllVo> selectNameByList(@Param("list") Collection list);

    List<Maintenanceuser> selectAll();
}