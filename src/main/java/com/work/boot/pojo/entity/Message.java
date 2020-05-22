package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * message
 * @author 
 */
@Data
public class Message implements Serializable {
    /**
     * 消息id

     */
    private String iid;

    /**
     * 消息名字
     */
    private String iname;

    /**
     * 消息内容
     */
    private String icontent;

    /**
     * 小区id
     */
    private String xid;

    /**
     * 创建管理员id
     */
    private String acid;

    /**
     * 修改消息管理员id
     */
    private String auid;

    /**
     * 创建消息时间
     */
    private Date icreatedate;

    /**
     * 更新消息时间
     */
    private Date iupdatedate;

    /**
     * 消息类型
     */
    private String itype;

    private static final long serialVersionUID = 1L;
}