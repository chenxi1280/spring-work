package com.work.boot.pojo.query;

import com.work.boot.pojo.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @Classname PayuserQuery
 * @Description TODO
 * @Date 2020/5/31 14:10
 * @CreateComputer by PC
 * @Created by cxd
 */
@Data
public class PayuserQuery extends PageQuery{
    private String phone ;
    private String paymentid;
    private String uname;

    private Long min;
    private Long max;
    private List<User> users;

}
