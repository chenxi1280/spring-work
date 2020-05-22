package com.work.boot.dao;

import com.work.boot.pojo.entity.Complaint;

public interface ComplaintDao {
    int deleteByPrimaryKey(String cid);

    int insert(Complaint record);

    int insertSelective(Complaint record);

    Complaint selectByPrimaryKey(String cid);

    int updateByPrimaryKeySelective(Complaint record);

    int updateByPrimaryKey(Complaint record);
}