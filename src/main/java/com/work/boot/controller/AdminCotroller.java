package com.work.boot.controller;


import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Admin;
import com.work.boot.pojo.entity.User;
import com.work.boot.service.AdminService;
import com.work.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminCotroller {

    @Resource  //自动装配
//    @Qualifier("userServiceImpl ")
    private UserService userService;
    @Resource  //自动装配
//    @Qualifier("userServiceImpl ")
    private AdminService adminService;

    @RequestMapping("/showadmin")
    public String showadmin() {
        return "showadmin";
    }

    //暂时无用
    @RequestMapping("/getAll")
    @ResponseBody
    public Result getAll() {


        Result result = userService.getAll();
        return result;

    }


    //业主信息表ajax请求地址
    @RequestMapping("/getadmin")
    @ResponseBody
    public Result getadmin(Integer page, Integer limit) {


        Result result = adminService.getadmin(page, limit);

        return result;

    }

    @RequestMapping("/geteditadmin")
    public String getEdit(String aid, Model model) {
        //通过id取找当前行的数据,并且返回给前端  session域     request域
        Admin admin = adminService.selectById(aid);
        model.addAttribute("admin", admin);
        return "editadmin";
    }


    // 添加user 接口
    @RequestMapping("/addadmin")
    @ResponseBody
    public Result addAdmin(Admin admin, HttpServletRequest request) {

        Result result = adminService.addAdmin(admin, request);
        return result;


    }
    @RequestMapping("/addadminhtml")
    String addadminh(){
        return "addadmin";
    }


    @RequestMapping("/test")
    String test(){
        return "test";
    }


    //  详细修改接口
    @RequestMapping("/toeditadmin")
    @ResponseBody
    public Result toedituser(Admin admin, HttpServletRequest request) {

        Result result = adminService.toedituser(admin, request);
        return result;

    }


    //删除user ajax接口
    @RequestMapping("/deladmin")
    @ResponseBody
    public Result delUserById(String[] ids) {

        Result result = adminService.delAdminById(ids);


        return result;
    }
//
//      "uid"  :    data.id,
//            "field":    field,
//            "value":    value


    //更新 user ajax 接口
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public Result updataUser(String aid, String field, String value) {

        Result result = adminService.updataAdmin(aid, field, value);


        return result;
    }


    // 模糊查询 ajax  接口
    @RequestMapping("/selectByLikeUsername")
    @ResponseBody
    public Result selectByLikeUsername(String username, String useruid, String userphone, Integer page, Integer limit) {


        Result result = adminService.getLikeUsername(username, useruid, userphone, page, limit);


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
        //先添加文件到系统中
        //先判定file是否大于0
        //获取文件名称 xx.jpg
        //对文件名称进行剪切 (.jpg)
        //去将文件添加到系统中
        //再将文件的信息添加到数据库中
        //先添加文件到系统中
        //先判定file是否大于0
        //获取文件名称 xx.jpg
        //对文件名称进行剪切 (.jpg)
        //去将文件添加到系统中
        //再将文件的信息添加到数据库中
//        String path  = request.getSession().getServletContext().getRealPath("/WEB_INF/video/");
//        MultimediaInfo vi = VideoUtil.vi(path + file.getOriginalFilename());
//        System.out.println(vi.getVideo().getSize().getWidth());
//        System.out.println(vi.getVideo().getSize().getHeight());

        Result result = userService.addUser(user, request);
        return result;


    }


}
