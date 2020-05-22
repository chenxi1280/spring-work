package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * admin
 * @author 
 */
@Data
public class Admin implements Serializable {
    /**
     * 管理员id
     */
    private String aid;

    /**
     * 名字
     */
    private String adminname;

    /**
     * 登录名
     */
    private String aname;

    /**
     * 管理员密码
     */
    private String apassword;

    /**
     * 小区id
     */
    private String xid;

    /**
     * 手机号码
     */
    private String aphoneid;

    /**
     * 权限id
     */
    private Integer typeid;

    /**
     * 创建时间
     */
    private Date acreatedate;

    /**
     * 管理员照片
     */
    private String aimg;

    private static final long serialVersionUID = 1L;
}