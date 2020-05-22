package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * noun
 * @author 
 */
@Data
public class Noun implements Serializable {
    private String xid;

    private String xname;

    private String xaddres;

    private static final long serialVersionUID = 1L;
}