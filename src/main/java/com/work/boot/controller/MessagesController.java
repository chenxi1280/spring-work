package com.work.boot.controller;

import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Message;
import com.work.boot.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    @RequestMapping("/getmessages")
    @ResponseBody
    public Result getmessagesAll(Integer page, Integer limit) {

        Result result = messagesService.getmessagesAll(page, limit);

        return result;
    }

    @RequestMapping("/showmessages")
    public String showmessages() {
        return "showmessages";
    }


    @RequestMapping("/addmessageshtml")
    public String addmessageshtml() {
        return "addmessages";
    }


    //删除user ajax接口
    @RequestMapping("/delmessages")
    @ResponseBody
    public Result delmessages(String[] ids) {

        Result result = messagesService.delmessages(ids);


        return result;
    }

    // 模糊查询 ajax  接口
    @RequestMapping("/selectByLikemessages")
    @ResponseBody
    public Result selectByLikemessages(String username, String useruid, Integer itype, Integer page, Integer limit) {


        Result result = messagesService.selectByLikemessages(username, useruid, itype, page, limit);


        return result;

    }

    // 模糊查询 ajax  接口
    @RequestMapping("/addmessages")
    @ResponseBody
    public Result addmessages(Message nMessage, HttpServletRequest request) {


        Result result = messagesService.addMessages(nMessage, request);


        return result;

    }

    @RequestMapping("/geteditmessage")
    public String geteditmessage(String iid, Model model) {

        Message nMessage = messagesService.selectById(iid);

        model.addAttribute("mesg", nMessage);

        return "editmessage";
    }

    @RequestMapping("/toeditmessages")
    @ResponseBody
    public Result toeditmessages(Message nMessage, HttpServletRequest request) {

        Result result = messagesService.updateMessage(nMessage);

        return result;

    }
}
