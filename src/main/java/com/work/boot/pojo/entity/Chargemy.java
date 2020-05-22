package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * chargemy
 * @author 
 */
@Data
public class Chargemy implements Serializable {
    private String sid;

    private Integer pstypeid;

    private Double smoney;

    private Date scompletiondate;

    private Integer sstate;

    private String uid;

    private String aid;

    private static final long serialVersionUID = 1L;
}