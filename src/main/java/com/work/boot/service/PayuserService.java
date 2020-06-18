package com.work.boot.service;

import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.entity.Payuser;
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

    ResponseDTO datebing();

    ResponseDTO ajaxaddpayment(PaymentQuery paymentQuery);

    ResponseDTO ajaxpaymentone();

    ResponseDTO ajaxpaymentpro();

    PageDTO ajaxlistpro(PayuserQuery payuserQuery);

    Payuser getpayUser(Integer userpayid);

    void paySuccess(Payuser payuser);

    ResponseDTO add(Payuser payuser);
}
