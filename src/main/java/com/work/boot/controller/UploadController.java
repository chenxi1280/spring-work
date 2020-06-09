package com.work.boot.controller;

import com.work.boot.pojo.dto.ResponseDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname UploadController
 * @Description TODO
 * @Date 2020/6/9 22:58
 * @CreateComputer by PC
 * @Created by cxd
 */
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {
//    @Resource
//    ImgCacheService imgCacheService;
//
//    //    img1
//    //    img1,img2
//    @RequestMapping("uploadFiles")
//    ResponseDTO uploadFiles(MultipartHttpServletRequest request) {
//        Collection<MultipartFile> values = request.getFileMap().values();
//        StringBuffer buffer = new StringBuffer();
//        String uploadPath = "/upload/";
//        String dir = request.getParameter("dir");
//        if (!StringUtils.isEmpty(dir)) {
//            if (dir.endsWith("/")) {// dir必须按照/结尾
//                uploadPath += dir;
//            } else {
//                uploadPath += dir + "/";//没有结尾我帮你结尾
//            }
//        }
//        for (MultipartFile f : values) {
//            String s = saveFile(f, uploadPath);// 保存图片
//            if (!StringUtils.isEmpty(s)) {// 保存成功，应该把这个地址放进缓存之中，然后写个定时任务，定时去删除缓存中的图片
//                if (imgCacheService.addCache(s)) {
//                    buffer.append(s).append(",");
//                }
//            }
//        }
//        if (buffer.length() > 0) {
//            buffer.delete(buffer.length() - 1, buffer.length());
//        }
//        return ResponseDTO.ok("上传成功", buffer.toString());
//    }
//
//    //   单独给wangeditor写个方法
//    @RequestMapping("wangEditorUploadFiles")
//    @ResponseBody
//    Map<String,Object> wangEditorUploadFiles(MultipartHttpServletRequest request) {
//        ResponseDTO res = this.uploadFiles(request);
//
//        if (res.getRes()) {// 成功
//            String object = res.getObject(String.class);
//            if (!StringUtils.isEmpty(object)) {
//                Map<String,Object> map=new HashMap<>();
//                map.put("errno",0);
//                map.put("data",object.split(","));
//                return map;
//            }
//        }
//        return null;
//    }
}
