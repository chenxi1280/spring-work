package com.work.boot.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * permission
 * @author 
 */
@Data
public class Permission implements Serializable {
    /**
     * 权限id
     */
    private Integer permissionId;

    /**
     * 权限的名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序字段:越小越排在前面
     */
    private Integer sort;

    /**
     * 标记字段：就是根据这个字段来判断是否有这个权限
     */
    private String flag;

    /**
     * 权限的说明
     */
    private String note;

    /**
     * 是否展示：展示1，不展示0
     */
    private Integer show;

    /**
     * 访问的地址
     */
    private String url;

    private static final long serialVersionUID = 1L;
}