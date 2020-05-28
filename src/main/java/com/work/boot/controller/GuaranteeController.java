package com.work.boot.controller;

import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.pojo.vo.GuaranteeAllVo;
import com.work.boot.service.GuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/guarantee")
@Controller
public class GuaranteeController extends FileController{

    @Resource
    private GuaranteeService guaranteeService;

    @RequestMapping("/addguarantee")
    public String addguarantee() {

        return "addguarantee";
    }

    @RequestMapping("/showmyguaratee")
    public String showmyguaratee() {

        return "showmyguaratee";
    }

    @RequestMapping("/getguarantee")
    public String getguarantee() {

        return "showguarantee";
    }

    @RequestMapping("/addgtype")
    public String addgtype() {

        return "addgtype";
    }

    @RequestMapping("/seeguarantee")
    public String seeguarantee(String rid, Model model) {
        GuaranteeAllVo guarantee = guaranteeService.getguarantee(rid);

        model.addAttribute("guarantee",guarantee);

        return "seeguarantee";
    }

    @RequestMapping("/getguarantees")
    @ResponseBody
    public Result getguarantees(Integer page, Integer limit) {

        Result result = guaranteeService.getguarantees(page, limit);

        return result;

    }


    @RequestMapping("/chengeguarantee")
    @ResponseBody
    public Result chengeguarantee(String rid) {

        Result result = guaranteeService.chengeguarantee(rid);

        return result;

    }

    @RequestMapping("/completeguarantee")
    @ResponseBody
    public Result completeguarantee(String rid) {

        Result result = guaranteeService.completeguarantee(rid);

        return result;

    }

    @RequestMapping("/toeditguarantee")
    @ResponseBody
    public Result toeditguarantee(Guarantee guarantee, MultipartFile rupimg){
        String imgname = saveFile(rupimg, "/upload/goods/");

        guarantee.setRimg(imgname);
        return guaranteeService.toeditguarantee(guarantee);
    }

    @RequestMapping("/selectByLikeguarantee")
    @ResponseBody
    public Result selectByLikeguarantee(String uphone,String uname,String maintenanceusername,Integer rstate, Integer page, Integer limit ){

        return guaranteeService.selectByLikeguarantee(uphone,uname,rstate,maintenanceusername,page,limit);
    }

    @RequestMapping("/ajaxaddguarantee")
    @ResponseBody
    public Result ajaxaddguarantee(GuaranteeAllVo guaranteeAllVo, MultipartFile rupimg, HttpServletRequest httpRequest){

        String imgname = saveFile(rupimg, "/upload/goods/");

        guaranteeAllVo.setRimg(imgname);

        return  guaranteeService.ajaxaddguarantee(guaranteeAllVo,httpRequest);

    }
    @RequestMapping("/getmyguarateelist")
    @ResponseBody
    public Result getmyguarateelist(HttpServletRequest httpRequest,Integer page, Integer limit){

        return  guaranteeService.getmyguarateelist(httpRequest,page,limit);

    }
    @RequestMapping("/delguarantee")
    @ResponseBody
    public Result delguarantee(String rid){

        return  guaranteeService.delguarantee(rid);

    }

    //用户完成评价的方法
//    @RequestMapping("/evaluationguarantee")
//    @ResponseBody
//    public Result evaluationguarantee(String rid , String rcontent){
//
//        return  guaranteeService.evaluationguarantee(httpRequest,page,limit);
//
//    }


}
