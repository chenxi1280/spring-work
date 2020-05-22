package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * paymy
 * @author 
 */
@Data
public class Paymy implements Serializable {
    private String pid;

    private Integer pstypeid;

    private Double pmoney;

    private Date pcompletiondate;

    private Integer pstate;

    private String uid;

    private static final long serialVersionUID = 1L;
}