package com.work.boot.test;

import java.util.Random;

/**
 * @Classname Test
 * @Description 测试类
 * @Date 2020/5/26 11:20
 * @CreateComputer by PC
 * @Created by cxd
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i <10 ; i++) {
            int i1 = new Random().nextInt(2 ) + 1;
            System.out.println(i1);
        }


    }
    int a(){
        return 1;
    }
    String a(int a){
        return "";
    }
}


