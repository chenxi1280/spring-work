package com.work.boot.service.impl;

import com.work.boot.dao.GuaranteeDao;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.service.GuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GuaranteeServiceImpl implements GuaranteeService {
    @Resource
    private GuaranteeDao guaranteeDao;

    @Override
    public Result getguarantees(Integer page, Integer limit) {

        Integer sta = (page - 1) * limit;
        List<Guarantee> list = guaranteeDao.selectAlllimet(sta, limit);

//        if (list != null && list.size() > 0) {
//            aidtoaname(list);
//        }

        Result result = new Result();

        result.setStatus(0);
        result.setItem(list);
        result.setTotal(guaranteeDao.getCount());
        return result;
    }

}
