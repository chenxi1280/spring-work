package com.work.boot.service.impl;

import com.work.boot.dao.PaymentDao;
import com.work.boot.dao.PayuserDao;
import com.work.boot.dao.UserDao;
import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.RData;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.entity.Payment;
import com.work.boot.pojo.entity.Payuser;
import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.query.PaymentQuery;
import com.work.boot.pojo.query.PayuserQuery;
import com.work.boot.pojo.query.UserQuery;
import com.work.boot.pojo.vo.PayuserVo;
import com.work.boot.service.PayuserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname PayuserServiceImpl
 * @Description TODO
 * @Date 2020/5/31 14:09
 * @CreateComputer by PC
 * @Created by cxd
 */
@Service
public class PayuserServiceImpl implements PayuserService {

    @Resource
    private PayuserDao payuserDao;
    @Resource
    private UserDao userDao;
    @Resource
    private PaymentDao paymentDao;


    @Override
    public PageDTO paylist(PayuserQuery payuserQuery) {

        List<PayuserVo> list = payuserDao.selectAllByQuery(payuserQuery);
        Integer count = payuserDao.selectCount(payuserQuery);
        List<User> userList = userDao.selectByListPay(list);
        List<Payment> listPay = paymentDao.selectByListPay(list);
        list.forEach(p -> {

            userList.forEach(user -> {
                if (user.getUid().equals(p.getUid())) {
                    p.setUusername(user.getUusername());
                }
            });

            listPay.forEach(payment -> {
                if (payment.getPaymentid() == p.getPaymentid()) {

                    p.setPaymentname(payment.getPaymentname());
                }

            });

        });


        return PageDTO.setPageData(count, list);
    }

    @Override
    public ResponseDTO ajaxpayment() {

        List<Payment> list = paymentDao.selectAll();

        return ResponseDTO.ok("ok", list);
    }

    @Override
    public ResponseDTO ajaxgetmoney(Integer id) {

        Payment payment = paymentDao.selectByPrimaryKey(id);


        return ResponseDTO.ok("ok", payment);
    }

    @Override
    @Transactional
    public ResponseDTO ajaxeditpayment(PaymentQuery paymentQuery) {


        int re = 0;
        try {
            re = paymentDao.updateByPrimaryKeySelective(paymentQuery);

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }
        return re == 0 ? ResponseDTO.fail("失败", 500) : ResponseDTO.ok("成功", 200);


    }

    @Override
    @Transactional
    public void deductions() {
        List<User> userList = userDao.selectAll();
        List<Payment> payments = paymentDao.selectAll();
        List<Payuser> payusers = payuserDao.selectAll();
        User usernew = new User();
        Map<String, List<Payuser>> collect = payusers.stream().collect(Collectors.groupingBy(Payuser::getUid));
        BigDecimal money = new BigDecimal(0);


        for (User user : userList) {
            if (user.getCstate() == 1) {
                if (user.getCarnumber() != 0) {
                    BigDecimal paymath = new BigDecimal(0);
                    for (Payment payment : payments) {
                        if (payment.getPaymentid() == 2) {
                            paymath = payment.getPaymentmoney();
                        }
                    }
                    BigDecimal multiply = paymath.multiply(new BigDecimal(user.getCarnumber()));
                    BigDecimal subtract = user.getMymoney().subtract(multiply);
                    if (subtract.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            int i = userDao.updatemoneyByid(user.getUid(), subtract);
                            Payuser payuser = new Payuser();
                            payuser.setPatstate(1);
                            payuser.setPaymentid(2);
                            payuser.setPaymoney(multiply);
                            payuser.setUid(user.getUid());
                            payuser.setPayuserdate(new Date());
                            payuserDao.insert(payuser);
                        } catch (Exception e) {
                            e.printStackTrace();
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }
                    }
                }
                if (user.getRoomarea() != 0) {
                    BigDecimal paymath = new BigDecimal(0);
                    for (Payment payment : payments) {
                        if (payment.getPaymentid() == 1) {
                            paymath = payment.getPaymentmoney();
                        }
                    }
                    BigDecimal multiply = paymath.multiply(new BigDecimal(user.getRoomarea()));
                    BigDecimal subtract = user.getMymoney().subtract(multiply);
                    if (subtract.compareTo(BigDecimal.ZERO) == 1) {
                        try {
                            int i = userDao.updatemoneyByid(user.getUid(), subtract);
                            Payuser payuser = new Payuser();
                            payuser.setPatstate(1);
                            payuser.setPaymentid(1);
                            payuser.setPaymoney(multiply);
                            payuser.setUid(user.getUid());
                            payuser.setPayuserdate(new Date());
                            payuserDao.insert(payuser);
                        } catch (Exception e) {
                            e.printStackTrace();
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }
                    }

                }

            }
        }


    }

    @Override
    public PageDTO ajaxrecharge(UserQuery userQuery) {


        return null;
    }

    @Override
    public ResponseDTO datebing() {
        List<Payment> payments = paymentDao.selectAll();
        Map<Integer, List<Payment>> collect1 = payments.stream().collect(Collectors.groupingBy(Payment::getPaymentid));
        List<Payuser> payusers = payuserDao.selectAll();
        Map<Integer, List<Payuser>> collect = payusers.stream().collect(Collectors.groupingBy(Payuser::getPaymentid));

        HashMap<String,Integer> stringHashMap = new HashMap<String,Integer>();

        collect.forEach((i,v) ->{
            BigDecimal cout = new BigDecimal(0);

            for (Payuser payuser : v) {
                BigDecimal paymoney = payuser.getPaymoney();
                cout =  cout.add(payuser.getPaymoney());
            }
            stringHashMap.put(collect1.get(i).get(0).getPaymentname(), cout.intValue());
        });
        List<RData> list = new ArrayList();
        stringHashMap.forEach((i,v) ->{
            RData rData = new RData();
            rData.setName(i);
            rData.setValue(v);
            list.add(rData);
        });




        return   ResponseDTO.ok("成功", list);
    }
}
