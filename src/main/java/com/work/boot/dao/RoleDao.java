package com.work.boot.dao;

import com.work.boot.pojo.entity.Role;
import com.work.boot.pojo.vo.RoleVO;

import java.util.List;

public interface RoleDao {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<RoleVO> selectHisRolesByRoles(String role);
}