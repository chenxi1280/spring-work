package com.work.boot.dao;

import com.work.boot.pojo.entity.Evaluation;

public interface EvaluationDao {
    int deleteByPrimaryKey(String rid);

    int insert(Evaluation record);

    int insertSelective(Evaluation record);

    Evaluation selectByPrimaryKey(String rid);

    int updateByPrimaryKeySelective(Evaluation record);

    int updateByPrimaryKey(Evaluation record);
}