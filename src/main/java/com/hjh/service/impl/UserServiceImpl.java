package com.hjh.service.impl;

import com.hjh.exception.GlobalException;
import com.hjh.mapper.UserMapper;
import com.hjh.pojo.User;
import com.hjh.service.UserService;
import com.hjh.utils.CookieUtil;
import com.hjh.utils.MD5Util;
import com.hjh.utils.UUIDUtil;
import com.hjh.vo.LoginVo;
import com.hjh.vo.RespBean;
import com.hjh.vo.RespBeanEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 洪锦辉
 * 2022/2/15
 */

@Service
public class UserServiceImpl implements UserService {
    private final static String COOKIE_NAME = "seckill_login_token";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String phone = loginVo.getMobile();
        String password = loginVo.getPassword();

        User user = userMapper.getUserByPhone(phone);

        if (user == null) {
            throw new GlobalException(RespBeanEnum.PHONE_NOT_FIND);
        }
        if (!MD5Util.fromPwdToDBPwd(password, user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.WRONG_PASSWORD);
        }
        // 生成cookie
        String ticket = UUIDUtil.uuid();
        //request.getSession().setAttribute(ticket, user);
        redisTemplate.opsForValue().set("user:" + ticket, user);
        CookieUtil.setCookie(request, response, COOKIE_NAME, ticket);
        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String ticket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(ticket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        if (user != null) {
            CookieUtil.setCookie(request, response, COOKIE_NAME, ticket);
        }
        return user;
    }
}
