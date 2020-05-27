package com.work.boot.dao;

import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.pojo.query.GuaranteeAllQury;
import com.work.boot.pojo.query.UserQuery;
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

    int updataState(String rid);

    int updataStateComplete(String rid);



    List<GuaranteeAllVo> selectLikeUserAll(@Param("uid")String uid,
                                             @Param("start") Integer page, @Param("limit")Integer limit);

    List<GuaranteeAllVo> selectByUid(@Param("list") List<UserQuery> list, @Param("rstate")  Integer rstate, @Param("start") Integer page, @Param("limit") Integer limit);


}