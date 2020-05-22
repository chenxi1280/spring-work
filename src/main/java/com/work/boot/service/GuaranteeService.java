package com.work.boot.service;

import com.work.boot.pojo.dto.Result;

public interface GuaranteeService {
    Result getguarantees(Integer page, Integer limit);
}
