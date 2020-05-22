package com.work.boot.dao;

import com.work.boot.pojo.entity.Guarantee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuaranteeDao {
    int deleteByPrimaryKey(String rid);

    int insert(Guarantee record);

    int insertSelective(Guarantee record);

    Guarantee selectByPrimaryKey(String rid);

    int updateByPrimaryKeySelective(Guarantee record);

    int updateByPrimaryKey(Guarantee record);

//    sql

    List<Guarantee> selectAll();

    List<Guarantee> selectAlllimet(@Param("start") Integer sta, @Param("limit") Integer limit);

    Integer getCount();

}