package com.work.boot.service.impl;

import com.work.boot.dao.EvaluationDao;
import com.work.boot.dao.UserDao;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Evaluation;
import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.vo.EvaluationVO;
import com.work.boot.pojo.vo.UserVO;
import com.work.boot.service.EvaluationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @Resource
    private UserDao userDao;
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

    @Override
    public ResponseDTO geteva() {
        List<EvaluationVO> list = evaluationDao.selectgeteva();
        List<UserVO> listu = userDao.selectUserBylist(list);
        Map<String, List<UserVO>> collect = listu.stream().collect(Collectors.groupingBy(User::getUid));

        list.forEach(evaluationVO -> {
            evaluationVO.setUname(collect.get(evaluationVO.getUid()).get(0).getUusername());
        });



        return ResponseDTO.ok("success", list);
    }
}
