package com.work.boot.task;

import com.work.boot.dao.PaymentDao;
import com.work.boot.dao.PayuserDao;
import com.work.boot.dao.UserDao;
import com.work.boot.service.PayuserService;
import com.work.boot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * creator：杜夫人
 * date: 2020/5/26
 */
@Component
public class SpringTaskTest {
    Logger logger = LoggerFactory.getLogger(SpringTaskTest.class);

    @Resource
    private PayuserService payuserService;

    @Scheduled(cron = "0/60 * * * * ?")
//    @Scheduled(cron = "0 15 10 L * ?")
    public void addUser() {// 单线程
        try {
//            System.err.println("定时扣钱了哦" + System.currentTimeMillis() / 1000);
//            payuserService.deductions();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    // 用在微信公众号 dev 刷新js的key 默认存活时间是两小时。
//    @Scheduled(fixedRate = 5000)// 每过5000毫秒就执行这个方法，不管你方法执行了多久
//    public void task2() {
//        // 如果这个方法开始的时候是0秒开始的
//        // 执行这个方法竟然花了三秒，请问，下次执行这个方法，时间是多少
//        // 是5秒
//        System.err.println("fixedRate" + System.currentTimeMillis() / 1000);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 5000)// 每过5000毫秒就执行这个方法，但是是以方法运行完之后开始算
//    public void task3() {
//        // 如果这个方法开始的时候是0秒开始的
//        // 执行这个方法竟然花了三秒，请问，下次执行这个方法，时间是多少
//        // 是8秒
//        System.err.println("fixedDelay" + System.currentTimeMillis() / 1000);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
