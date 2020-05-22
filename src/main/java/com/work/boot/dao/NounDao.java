package com.work.boot.dao;

import com.work.boot.pojo.entity.Noun;

public interface NounDao {
    int deleteByPrimaryKey(String xid);

    int insert(Noun record);

    int insertSelective(Noun record);

    Noun selectByPrimaryKey(String xid);

    int updateByPrimaryKeySelective(Noun record);

    int updateByPrimaryKey(Noun record);
}