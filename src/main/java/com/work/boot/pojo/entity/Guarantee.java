package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * guarantee
 * @author 
 */
@Data
public class Guarantee implements Serializable {
    /**
     * 报修id
     */
    private String rid;

    /**
     * 报修名字
     */
    private String rname;

    /**
     * 报修内容
     */
    private String rcontent;

    /**
     * 提交时间
     */
    private Date rpublicdate;

    /**
     * 确定时间
     */
    private Date raccpetdate;

    /**
     * 完成日期
     */
    private Date rcompletiondate;

    /**
     * 维修人员id
     */
    private Integer maintenanceuserid;

    /**
     * 状态
     */
    private Integer rstate;

    /**
     * 维修费用
     */
    private String revaluation;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 管理员id
     */
    private String aid;

    /**
     * 地址
     */
    private String raddress;

    /**
     * 图片
     */
    private String rimg;

    private static final long serialVersionUID = 1L;
}