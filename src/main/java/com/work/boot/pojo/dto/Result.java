package com.work.boot.pojo.dto;

import java.util.List;

/**
 * 专门用于封装返回的数据
 */
public class Result {

    private Integer status;

    private String message;

    private Integer total;

    private List item;

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getItem() {
        return item;
    }

    public void setItem(List item) {
        this.item = item;
    }
}
