package com.work.boot.util.password;

/**
 * creator：cxd
 * date: 2020/5/28
 */
public class PasswordUtil {
    private final static String SALT = "123";
    public static String encodePassword(String password) {
        String salt = "{{" + SALT + "}}";// 盐值准备好
        MD5Code md5Code = new MD5Code();
        String md5ofStr = md5Code.getMD5ofStr(salt + password);
        for (int i = 0; i < 3; i++) {
            md5ofStr = md5Code.getMD5ofStr(md5ofStr);
        }
        return md5ofStr;
    }


}
