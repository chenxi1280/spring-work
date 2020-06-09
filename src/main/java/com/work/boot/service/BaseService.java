package com.work.boot.service;


import com.work.boot.pojo.vo.PermissionVO;
import com.work.boot.pojo.vo.RoleVO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * creator：杜夫人
 * date: 2020/6/1
 */
public interface BaseService {
    /**
     * 把角色的集合设置上权限属性
     *
     * @param roles
     * @param permissionVOS
     * @return
     */
    default List<RoleVO> getRoleVOList(List<RoleVO> roles, List<PermissionVO> permissionVOS) {
        Map<Integer, List<PermissionVO>> collect = permissionVOS.stream().collect(Collectors.groupingBy(PermissionVO::getPermissionId));
        for (RoleVO r : roles) {
            String permissions = r.getPermissions();
            if (!StringUtils.isEmpty(permissions)) {
                String[] split = permissions.split(",");
                List<PermissionVO> li = new ArrayList<>();
                for (String s : split) {
                    List<PermissionVO> permissionVOS1 = collect.get(Integer.valueOf(s));
                    if (!CollectionUtils.isEmpty(permissionVOS1)) {
                        PermissionVO p = permissionVOS1.get(0);
                        li.add(p);
                    }

                }
                r.setPermissionVOS(li);
            }
        }
        return roles;
    }
}
