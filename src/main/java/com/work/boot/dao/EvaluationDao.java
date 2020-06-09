package com.work.boot.dao;

import com.work.boot.pojo.entity.Evaluation;
import com.work.boot.pojo.vo.EvaluationVO;

import java.util.List;

public interface EvaluationDao {
    int deleteByPrimaryKey(String rid);

    int insert(Evaluation record);

    int insertSelective(Evaluation record);

    Evaluation selectByPrimaryKey(String rid);

    int updateByPrimaryKeySelective(Evaluation record);

    int updateByPrimaryKey(Evaluation record);

    List<EvaluationVO> selectgeteva();

    Integer inserts(Evaluation evaluation);
}