package com.work.boot.dao;

import com.work.boot.pojo.entity.Role;
import com.work.boot.pojo.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<RoleVO> selectHisRolesByRoles(String role);

    List<Role> selectByPermissions(@Param("ids") Set<String> collect);

    List<RoleVO> getSystemRoles();

}