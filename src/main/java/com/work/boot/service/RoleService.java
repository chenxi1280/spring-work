package com.work.boot.service;

import com.work.boot.pojo.dto.PageDTO;

/**
 * @Classname RoleService
 * @Description TODO
 * @Date 2020/6/10 0:39
 * @CreateComputer by PC
 * @Created by cxd
 */
public interface RoleService {
    // 获取系统级别的所有角色
    PageDTO getSystemRoles();
}
