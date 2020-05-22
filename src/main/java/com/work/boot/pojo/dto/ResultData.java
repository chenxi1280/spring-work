package com.work.boot.pojo.dto;

import java.util.List;

/**
 * create:XXXXX
 * date:2020/5/21
 */

public class ResultData {
    private List<RData> list;

    private String msg;

    public List<RData> getList() {
        return list;
    }

    public void setList(List<RData> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
