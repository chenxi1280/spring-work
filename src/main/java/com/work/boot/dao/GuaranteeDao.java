package com.work.boot.dao;

import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.pojo.vo.GuaranteeAllVo;
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

    List<GuaranteeAllVo> selectAlllimet(@Param("start") Integer sta, @Param("limit") Integer limit);

    Integer getCount();

    GuaranteeAllVo selectByid(String rid);
}