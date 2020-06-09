package com.work.boot.dao;

import com.work.boot.pojo.entity.Permission;
import com.work.boot.pojo.vo.PermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PermissionDao {
    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<PermissionVO> selectPermissionsBySet(@Param("ids") Set<String> paramSet);
}