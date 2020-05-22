package com.work.boot.dao;

import com.work.boot.pojo.entity.Paytype;

public interface PaytypeDao {
    int deleteByPrimaryKey(Integer pstypeid);

    int insert(Paytype record);

    int insertSelective(Paytype record);

    Paytype selectByPrimaryKey(Integer pstypeid);

    int updateByPrimaryKeySelective(Paytype record);

    int updateByPrimaryKey(Paytype record);
}