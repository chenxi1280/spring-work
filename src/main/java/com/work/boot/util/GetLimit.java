package com.work.boot.util;

public class GetLimit {
    public static Integer getPage(Integer page, Integer limit) {
        return (page - 1) * limit;
    }
}
