package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * complaint
 * @author 
 */
@Data
public class Complaint implements Serializable {
    private String cid;

    private String cname;

    private String ccontent;

    private Date cpublicdate;

    private Date caccpetdate;

    private Date ccompletiondate;

    private Integer cstate;

    private String cevaluation;

    private String uid;

    private String aid;

    private String caddress;

    private String cimg;

    private static final long serialVersionUID = 1L;
}