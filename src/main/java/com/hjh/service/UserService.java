package com.hjh.service;

import com.hjh.pojo.User;
import com.hjh.vo.LoginVo;
import com.hjh.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 洪锦辉
 * 2022/2/15
 */
public interface UserService {
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);
    User getUserByCookie(String ticket, HttpServletRequest request, HttpServletResponse response);
}
