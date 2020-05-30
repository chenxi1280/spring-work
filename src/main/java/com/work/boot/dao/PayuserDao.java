package com.work.boot.dao;

import com.work.boot.pojo.entity.Payuser;

public interface PayuserDao {
    int deleteByPrimaryKey(Integer userpayid);

    int insert(Payuser record);

    int insertSelective(Payuser record);

    Payuser selectByPrimaryKey(Integer userpayid);

    int updateByPrimaryKeySelective(Payuser record);

    int updateByPrimaryKey(Payuser record);
}