package com.work.boot.controller;



import com.work.boot.pojo.dto.PageDTO;
import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Guarantee;
import com.work.boot.pojo.entity.Role;
import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.query.UserAddQurey;
import com.work.boot.pojo.query.UserQuery;
import com.work.boot.pojo.query.UserQueryS;
import com.work.boot.pojo.query.UserQuerySS;
import com.work.boot.pojo.vo.RoleVO;
import com.work.boot.service.RoleService;
import com.work.boot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

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



    @RequestMapping("/addusercstatus")
    public String addusermoney() {

        return "addusercstatus";

    }


    @RequestMapping("/addusermoney")
    @ResponseBody
    public ResponseDTO addusermoney(String uid, BigDecimal money) {


        return userService.addusermoney(uid,money);


    }

    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO add(User user,MultipartFile ruimg) {


        String imgname = saveFile(ruimg, "/upload/user/");
        user.setUimg(imgname);
        return userService.add(user);


    }

    @RequestMapping("/editbyid")
    @ResponseBody
    public ResponseDTO editbyid(User user,MultipartFile pic) {
        String imgname = saveFile(pic, "/upload/user");
        user.setUimg(imgname);
        return userService.edit(user);


    }

    @RequestMapping("/edit")
    @ResponseBody
    public ResponseDTO edit(User user) {

        return userService.edit(user);


    }

    @RequestMapping("/useraddmoney")
    public String useraddmoney(){

        return "useraddmoney";
    }

    @RequestMapping("/useraddusermoney")
    @ResponseBody
    public ResponseDTO useraddusermoney(UserAddQurey user) {

        return userService.useraddusermoney(user);


    }


    @RequestMapping("/ajaxadminlist")
    @ResponseBody
    public PageDTO ajaxadminlist(UserQuerySS userQuery) {
//        PageDTO systemRoles = roleService.getSystemRoles();
//        model.addAttribute("systemRoles", systemRoles.getData());
        return userService.ajaxadminlist(userQuery);


    }


    // 获取用户的角色
    @RequestMapping("getUserRoles/{phone}")
    @ResponseBody
    List<RoleVO> getUserRoles(@PathVariable String phone) {
        return userService.selectHisRolesByPhone(phone);
    }

    // 对用户进行角色和权限分配
    @RequestMapping("dispatchUserPermission/{uid}")
    @ResponseBody
    ResponseDTO dispatchUserPermission(@PathVariable String uid, @RequestBody List<Role> roles) {

        return userService.dispatchUserPermission(uid, roles);
    }

    // 对用户进行角色和权限分配
    @RequestMapping("deleteadmin")
    @ResponseBody
    ResponseDTO deleteadmin(String uid) {

        return userService.deleteadmin(uid);
    }

    @RequestMapping("editadmin")
    @ResponseBody
    ResponseDTO editadmin(User user) {

        return userService.editadmin(user);
    }

    @RequestMapping("edituserpro")
    @ResponseBody
    ResponseDTO edituserpro(User user) {

        return userService.edituserpro(user);
    }

//    @RequestMapping("/toeditguarantee")
//    @ResponseBody
//    public Result toeditguarantee(Guarantee guarantee, MultipartFile rupimg){
//        String imgname = saveFile(rupimg, "/upload/goods/");
//
//        guarantee.setRimg(imgname);
//        return guaranteeService.toeditguarantee(guarantee);
//    }



}
