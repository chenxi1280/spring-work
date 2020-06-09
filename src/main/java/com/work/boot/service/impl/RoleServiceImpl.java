package com.work.boot.service.impl;

import com.work.boot.dao.RoleDao;
import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.vo.PermissionVO;
import com.work.boot.pojo.vo.RoleVO;
import com.work.boot.service.RoleService;
import com.work.boot.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname RoleServiceImpl
 * @Description TODO
 * @Date 2020/6/10 0:39
 * @CreateComputer by PC
 * @Created by cxd
 */

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleDao roleDao;


    @Resource
    UserService userService;


    @Override
    public PageDTO getSystemRoles() {
        List<RoleVO> roles = roleDao.getSystemRoles();

        List<PermissionVO> permissionVOS = userService.selectHisPermissionByRoles(roles);

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

        return PageDTO.setPageData(roles.size(), roles);
    }
}
