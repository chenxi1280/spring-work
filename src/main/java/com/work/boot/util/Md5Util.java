package com.work.boot.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 专门用来对密码加密使用
 */
public class Md5Util {

    public static String encryption(String password, String username) {

        Md5Hash md5Hash = new Md5Hash(password, username, 100);

        return md5Hash.toString();

    }
//
//    public static void main(String[] args) {
//        System.out.println(encryption("1","1"));
//    }

}
