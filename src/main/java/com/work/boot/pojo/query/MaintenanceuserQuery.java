package com.work.boot.pojo.query;

import lombok.Data;

/**
 * @Classname MaintenanceuserQuery
 * @Description TODO
 * @Date 2020/6/10 10:16
 * @CreateComputer by PC
 * @Created by cxd
 */
@Data
public class MaintenanceuserQuery extends PageQuery{

    private String phone;

    private String name;

}
