package com.itheima.mm.service;

import com.itheima.mm.pojo.User;

/**
 * 用户业务接口
 */
public interface UserService {
    /**
     * 根据用户名获取用户对象
     * @param username 用户名
     * @return 用户对象
     */
    User findUserByUsername(String username);
}
