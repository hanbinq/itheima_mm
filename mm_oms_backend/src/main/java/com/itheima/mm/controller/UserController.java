package com.itheima.mm.controller;

import com.itheima.framework.annotation.HmComponent;
import com.itheima.framework.annotation.HmRequestMapping;
import com.itheima.framework.annotation.HmSetter;
import com.itheima.mm.base.BaseController;
import com.itheima.mm.common.GlobalConst;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import com.itheima.mm.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户控制器（子控制器）
 */
@HmComponent
@Slf4j
public class UserController extends BaseController {

    // 控制器需要一个用户业务对象，通过自定义注解注入到当前控制器对象
    @HmSetter("userService")
    private UserService userService;
    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @HmRequestMapping("/user/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 接收前端的数据，并封装到pojo对象
        User userForm = parseJSON2Object(request, User.class);
        log.debug("user:{}", userForm);
        // 调用Service
        User daoUser = userService.findUserByUsername(userForm.getUsername());
        // 判断用户是否为空
        if (daoUser == null){
            Result result = new Result(false, "登录失败，用户不存在");
            printResult(response, result);
            return;
        }
        // 用户存在，判断密码是否一致
        if (daoUser.getPassword().equals(userForm.getPassword())){
            // 登录成功，创建session，并把对象存入Session
            HttpSession session = request.getSession(true);
            session.setAttribute(GlobalConst.SESSION_KEY_USER, daoUser);
            printResult(response, new Result(true, "登录成功", daoUser.getUsername()));
        }else {
            printResult(response, new Result(false, "登录失败，用户名和密码不一致"));
        }
    }

    @HmRequestMapping("/user/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 销毁会话，如果没有会话，不需要创建会话
        HttpSession session = request.getSession(false);
        if (session != null){
            // 销毁Session
            session.invalidate();
        }
        printResult(response, new Result(true,"登出成功"));
    }
}
