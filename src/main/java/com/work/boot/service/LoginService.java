package com.work.boot.service;

import com.work.boot.pojo.dto.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    Result login(String username, String password, String code, String close, HttpServletRequest request, HttpServletResponse response);
}
