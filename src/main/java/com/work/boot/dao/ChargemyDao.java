package com.work.boot.dao;

import com.work.boot.pojo.entity.Chargemy;

public interface ChargemyDao {
    int deleteByPrimaryKey(String sid);

    int insert(Chargemy record);

    int insertSelective(Chargemy record);

    Chargemy selectByPrimaryKey(String sid);

    int updateByPrimaryKeySelective(Chargemy record);

    int updateByPrimaryKey(Chargemy record);
}