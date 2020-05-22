package com.work.boot.dao;

import com.work.boot.pojo.entity.Paymy;

public interface PaymyDao {
    int deleteByPrimaryKey(String pid);

    int insert(Paymy record);

    int insertSelective(Paymy record);

    Paymy selectByPrimaryKey(String pid);

    int updateByPrimaryKeySelective(Paymy record);

    int updateByPrimaryKey(Paymy record);
}