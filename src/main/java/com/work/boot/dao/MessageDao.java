package com.work.boot.dao;

import com.work.boot.pojo.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageDao {
    int deleteByPrimaryKey(String iid);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(String iid);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> selectAll();

    List<Message> selectByLikemessages(@Param("username") String username, @Param("useruid") String useruid,
                                        @Param("itype") Integer itype, @Param("page") Integer page, @Param("limit") Integer limit);

    Message selectById(String iid);


    List<Message> selectAlllimet(@Param("start") Integer sta, @Param("limit") Integer limit);

    Integer getCount();

    List<Message> getmessagesimg();

}