package com.work.boot.pojo.vo;

import com.work.boot.pojo.entity.User;
import lombok.Data;

/**
 * @Classname UserVO
 * @Description TODO
 * @Date 2020/6/3 11:52
 * @CreateComputer by PC
 * @Created by cxd
 */
@Data
public class UserVO extends User {
    private String code;
    private String note;

}