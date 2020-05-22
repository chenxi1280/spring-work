package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    private String uid;

    /**
     * 登录名

     */
    private String uname;

    /**
     * 用户名字
     */
    private String uusername;

    /**
     * 用户密码
     */
    private String upassword;

    /**
     * 是否为小区业主
     */
    private Integer cstate;

    /**
     * 更改管理员id
     */
    private String aid;

    /**
     * 小区id
     */
    private String xid;

    /**
     * 房屋id
     */
    private String ubroom;

    /**
     * 手机号码
     */
    private String uphoneid;

    /**
     * 用户照片
     */
    private String uimg;

    /**
     * 权限id
     */
    private Integer typeid;

    /**
     * 创建时间
     */
    private Date ucreatedate;

    /**
     * 更新时间
     */
    private Date uupdatedate;

    private static final long serialVersionUID = 1L;
}