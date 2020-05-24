package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * maintenanceuser
 * @author 
 */
@Data
public class Maintenanceuser implements Serializable {
    /**
     * 维修人员id
     */
    private Integer maintenanceuserid;

    /**
     * 维修人员名字
     */
    private String maintenanceusername;

    /**
     * 维修人员状态
     */
    private Integer maintenanceuseridstate;

    /**
     * 维修人员电话
     */
    private String maintenanceuserphone;

    private static final long serialVersionUID = 1L;
}