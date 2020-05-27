package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * evaluation
 * @author 
 */
@Data
public class Evaluation implements Serializable {
    /**
     * 维修评价表 维修id 主键
     */
    private String rid;

    /**
     * 维修评价表 userid 
     */
    private String uid;

    /**
     * 评价内容
     */
    private String context;

    /**
     * 评分
     */
    private Integer evaluation;

    /**
     * 评价时间
     */
    private Date createtime;

    private static final long serialVersionUID = 1L;
}