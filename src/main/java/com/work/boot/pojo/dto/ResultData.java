package com.work.boot.pojo.dto;

import java.util.List;

/**
 * create:XXXXX
 * date:2020/5/21
 */

public class ResultData {
    // list 属性 只放 RData 类的数据
    private List<RData> list;
    // 状态
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
