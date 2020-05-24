package com.work.boot.service;

import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.pojo.vo.GuaranteeAllVo;

public interface GuaranteeService {
    Result getguarantees(Integer page, Integer limit);

    GuaranteeAllVo getguarantee(String rid);

    Result chengeguarantee(String rid);
}
