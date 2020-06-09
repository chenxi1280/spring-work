package com.work.boot.controller;



import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.query.UserQuery;
import com.work.boot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{


    @Resource  //自动装配
//    @Qualifier("userServiceImpl ")
    private UserService userService;

    @RequestMapping("/showuser")
    public String showuser() {
        return "showuser";
    }

    //暂时无用
    @RequestMapping("/getAll")
    @ResponseBody
    public Result getAll() {


        Result result = userService.getAll();
        return result;

    }

    //业主信息表ajax请求地址
    @RequestMapping("/homeuser")
    @ResponseBody
    public ResponseDTO homeuser() {



        UserQuery userQuery = new UserQuery();

        userQuery.setUid(getUserId());

        return  userService.homeuser(userQuery);


    }


    //业主信息表ajax请求地址
    @RequestMapping("/getusers")
    @ResponseBody
    public Result getusers(Integer page, Integer limit) {


        Result result = userService.getusers(page, limit);

        return result;

    }

    // 预留查看 个人详细接口
    @RequestMapping("/seeuser")
    @ResponseBody
    public Result seeuser(String uid) {

        Result result = userService.seeuser(uid);

        return result;

    }

    // 获取 user
    @RequestMapping("/getedituser")
    public String getEdit(String uid, Model model) {
        //通过id取找当前行的数据,并且返回给前端  session域     request域
        User user = userService.selectById(uid);
        model.addAttribute("user", user);
        return "edituser";
    }


    //  详细修改接口
    @RequestMapping("/toedituser")
    @ResponseBody
    public Result toedituser(User user, HttpServletRequest request) {

        Result result = userService.toedituser(user, request);
        return result;

    }


    //删除user ajax接口
    @RequestMapping("/delUser")
    @ResponseBody
    public Result delUserById(String[] ids) {

        Result result = userService.delUserById(ids);
//        RequestContextHolder.getRequestAttributes().get


        return result;
    }
//
//      "uid"  :    data.id,
//            "field":    field,
//            "value":    value


    //更新 user ajax 接口
    @RequestMapping("/updateUser")
    @ResponseBody
    public Result updataUser(String uid, String field, String value) {

        Result result = userService.updataUser(uid, field, value);


        return result;
    }


    // 模糊查询 ajax  接口
    @RequestMapping("/selectByLikeUsername")
    @ResponseBody
    public Result selectByLikeUsername(String username, String useruid, String userphone, Integer page, Integer limit) {


        Result result = userService.getLikeUsername(username, useruid, userphone, page, limit);


        return result;

    }


    //上传图片接口
    @RequestMapping("/uploadimg")
    @ResponseBody
    public Result uploadImg(MultipartFile uimg, HttpServletRequest request) {


        Result result = userService.adduimg(uimg, request);
        return result;

    }


    // 添加user 接口
    @RequestMapping("/adduser")
    @ResponseBody
    public Result addUser(User user, HttpServletRequest request) {

        Result result = userService.addUser(user, request);
        return result;


    }

    @RequestMapping("/adduserhtml")

    public String adduserhtml() {

        return "adduser";

    }

    @RequestMapping("/addmoneyhtml")

    public String addmoneyhtml() {

        return "addmoney";

    }

    @RequestMapping("/useraddmoney")
    public String useraddmoney() {

        return "useraddmoney";

    }

    @RequestMapping("/addusercstatus")
    public String addusermoney() {

        return "addusercstatus";

    }


    @RequestMapping("/addusermoney")
    @ResponseBody
    public ResponseDTO addusermoney(String uid, BigDecimal money) {


        return userService.addusermoney(uid,money);


    }

}
