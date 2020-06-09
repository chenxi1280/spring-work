package com.work.boot.pojo.vo;


import com.work.boot.pojo.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * creator：cxd
 * date: 2020/5/28
 */
@Data
public class RoleVO extends Role {

    List<PermissionVO> permissionVOS;
}
