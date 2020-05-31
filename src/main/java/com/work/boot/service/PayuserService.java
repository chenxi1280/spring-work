package com.work.boot.service;

import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.query.PaymentQuery;
import com.work.boot.pojo.query.PayuserQuery;
import com.work.boot.pojo.query.UserQuery;

/**
 * @Classname PayuserService
 * @Description TODO
 * @Date 2020/5/31 14:09
 * @CreateComputer by PC
 * @Created by cxd
 */
public interface PayuserService {

    PageDTO paylist(PayuserQuery payuserQuery);

    ResponseDTO ajaxpayment();

    ResponseDTO ajaxgetmoney(Integer id);

    ResponseDTO ajaxeditpayment(PaymentQuery paymentQuery);

    void deductions();

    PageDTO ajaxrecharge(UserQuery userQuery);
}
