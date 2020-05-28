package com.work.boot.service.impl;

import com.work.boot.dao.EvaluationDao;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Evaluation;
import com.work.boot.service.EvaluationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Classname EvaluationServiceImpl
 * @Description TODO
 * @Date 2020/5/28 23:39
 * @CreateComputer by PC
 * @Created by cxd
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Resource
    private EvaluationDao evaluationDao;
    @Override
    public Result addeva(Evaluation evaluation) {
        evaluation.setCreatetime(new Date());


        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            int insert = evaluationDao.insert(evaluation);
            if (insert == 1) {
                result.setStatus(200);
                result.setMessage("操作成功");

            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return result ;
    }
}
