package com.work.boot.pojo.query;

import lombok.Data;

/**
 * @Classname UserQuerySS
 * @Description TODO
 * @Date 2020/6/10 9:57
 * @CreateComputer by PC
 * @Created by cxd
 */
@Data
public class UserQuerySS extends PageQuery {

    private String phone;
    private String realName;

}
