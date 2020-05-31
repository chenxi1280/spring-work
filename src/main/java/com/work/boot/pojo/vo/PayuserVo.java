package com.work.boot.pojo.vo;

import com.work.boot.pojo.entity.Payuser;
import lombok.Data;

/**
 * @Classname PayuserVo
 * @Description TODO
 * @Date 2020/5/31 14:13
 * @CreateComputer by PC
 * @Created by cxd
 */
@Data
public class PayuserVo extends Payuser {

    private String uusername ;

    private String paymentname;

}
