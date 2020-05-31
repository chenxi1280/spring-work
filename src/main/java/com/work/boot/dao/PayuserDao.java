package com.work.boot.dao;

import com.work.boot.pojo.entity.Payuser;
import com.work.boot.pojo.query.PayuserQuery;
import com.work.boot.pojo.vo.PayuserVo;

import java.util.List;

public interface PayuserDao {
    int deleteByPrimaryKey(Integer userpayid);

    int insert(Payuser record);

    int insertSelective(Payuser record);

    Payuser selectByPrimaryKey(Integer userpayid);

    int updateByPrimaryKeySelective(Payuser record);

    int updateByPrimaryKey(Payuser record);

//    Sql
    List<PayuserVo> selectAllByQuery(PayuserQuery payuserQuery);

    Integer selectCount(PayuserQuery payuserQuery);

    List<Payuser> selectAll();

}