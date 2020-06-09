package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * role
 * @author 
 */
@Data
public class Role implements Serializable {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 标记：这个标记就是存到用户session里边，就是根据这个标记来判断用户是否有这个角色
     */
    private String flag;

    /**
     * 角色是备注或者说明：给分配角色和权限的人看的
     */
    private String note;

    /**
     * 一个用户权限组的的字符串，按照逗号拆分
     */
    private String permissions;

    private static final long serialVersionUID = 1L;
}