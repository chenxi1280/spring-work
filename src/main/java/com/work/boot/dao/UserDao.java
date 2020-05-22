package com.work.boot.dao;

import com.work.boot.pojo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    // 自己的 接口

    List<User> selectAll();

    List<User> getusers(@Param("start") Integer page, @Param("limits") Integer limti);

    void updateByuid(@Param("uid") String uid, @Param("field") String field, @Param("value") String value);

    List<User> getLikeUsername(@Param("username") String username, @Param("useruid") String useruid,
                               @Param("userphone") String userphone, @Param("start") Integer page,
                               @Param("limits") Integer limti);

    Integer getCount();

    User selectByUserName(String uname);

    User selectcheckUser(@Param("uname") String uname, @Param("upassword") String upassword);

    User selectById(String uid);
}