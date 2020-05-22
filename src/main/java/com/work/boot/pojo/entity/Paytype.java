package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * paytype
 * @author 
 */
@Data
public class Paytype implements Serializable {
    private Integer pstypeid;

    private String pstypename;

    private static final long serialVersionUID = 1L;
}