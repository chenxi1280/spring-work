package com.work.boot.dao;

import com.work.boot.pojo.entity.Admin;
import com.work.boot.pojo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminDao {
    int deleteByPrimaryKey(String aid);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String aid);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    //sql

    List<Admin> selectAll();

    String selectanameById(String aid);


    Admin selectByAdminName(String uname);

    Admin checkAdmin(@Param("aname") String aname, @Param("apassword") String apassword);

    List<Admin> getadmin(@Param("start") Integer sta, @Param("limit") Integer limit);

    Integer getCount();


    List<User> getLikeAdmin(@Param("username") String username, @Param("useruid") String useruid,
                            @Param("userphone") String userphone, @Param("start") Integer page,
                            @Param("limits") Integer limti);


    Admin selectById(String aid);

    void updateByuid(@Param("aid") String aid, @Param("field") String field, @Param("value") String value);

}