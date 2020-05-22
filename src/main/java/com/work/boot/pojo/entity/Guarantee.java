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
    private String rid;

    private String rname;

    private String rcontent;

    private Date rpublicdate;

    private Date raccpetdate;

    private Date rcompletiondate;

    private Integer rstate;

    private String revaluation;

    private String uid;

    private String aid;

    private String raddress;

    private String rimg;

    private static final long serialVersionUID = 1L;
}