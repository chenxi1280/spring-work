package com.work.boot.pojo.query;

import com.work.boot.pojo.entity.User;
import lombok.Data;

/**
 * @Classname UserQueryS
 * @Description TODO
 * @Date 2020/6/3 0:04
 * @CreateComputer by PC
 * @Created by cxd
 */
@Data
public class UserQueryS extends User {
    private String code;
    private String phone;

}
