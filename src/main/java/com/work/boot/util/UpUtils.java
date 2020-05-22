package com.work.boot.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;


public class UpUtils {

    public static void upfile(MultipartFile file, HttpServletRequest request) {
        //file 是你当前上传的文件 request 当前请求
        //获取当前服务器路径
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/img/");
        System.out.println("当前服务器的路径" + realPath);
        //在本地能够看见你的上传功能成功了-->源代码项目的地址-->只是为了测试是否成功用的
        String basePath = "D:\\bs\\work\\src\\main\\webapp\\WEB-INF\\img\\";
        //获取我们的地址
        File file1 = new File(realPath);
        //检测当前路径是否存在
        if (!file1.exists()) {
            file1.mkdirs();
        }

        //只是为了完善检测功能的成功-->如果进行项目发布以下内容必须删除
        File file2 = new File(basePath);
        if (!file2.exists()) {
            file2.mkdirs();
        }

        //获取我们上传的文件的名字
        String orgName = file.getOriginalFilename();
        try {
            FileOutputStream fos = new FileOutputStream(realPath + orgName, true);
            FileOutputStream fos1 = new FileOutputStream(basePath + orgName, true);
            fos.write(file.getBytes());
            fos1.write(file.getBytes());
            fos1.flush();
            fos.flush();
            fos1.close();
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

}
