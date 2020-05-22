package com.work.boot.service;

import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.entity.Message;

import javax.servlet.http.HttpServletRequest;

public interface MessagesService {

    Result delmessages(String... ids);

    Result getmessagesAll(Integer page, Integer limit);

    Result selectByLikemessages(String username, String useruid, Integer itype, Integer page, Integer limit);

    Result addMessages(Message nMessage, HttpServletRequest request);

    Message selectById(String iid);

    Result updateMessage(Message nMessage);

}
