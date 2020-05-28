package com.work.boot.service;

import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.pojo.vo.GuaranteeAllVo;

import javax.servlet.http.HttpServletRequest;

public interface GuaranteeService {
    Result getguarantees(Integer page, Integer limit);

    GuaranteeAllVo getguarantee(String rid);

    Result chengeguarantee(String rid);

    Result completeguarantee(String rid);

    Result toeditguarantee(Guarantee guarantee);

    Result selectByLikeguarantee(String uphone, String username, Integer rstate, String maintenanceusername, Integer page, Integer limit);

    Result ajaxaddguarantee(GuaranteeAllVo guaranteeAllVo, HttpServletRequest request);

    Result getmyguarateelist(HttpServletRequest httpRequest, Integer page, Integer limit);

    Result delguarantee(String rid);
}
