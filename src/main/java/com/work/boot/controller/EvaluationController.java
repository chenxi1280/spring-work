package com.work.boot.controller;

import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Evaluation;
import com.work.boot.service.EvaluationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Classname EvaluationController
 * @Description TODO
 * @Date 2020/5/28 23:38
 * @CreateComputer by PC
 * @Created by cxd
 */
@RequestMapping("/evaluation")
@Controller
public class EvaluationController {
    @Resource
    public EvaluationService evaluationService;

    @RequestMapping("/addeva")
    @ResponseBody
    public Result addeva(Evaluation evaluation){

        return evaluationService.addeva(evaluation);
    }

    @RequestMapping("/geteva")
    @ResponseBody
    public ResponseDTO geteva(){

        return evaluationService.geteva();
    }


}
