package com.itheima.mm.service.impl;

import com.itheima.mm.dao.UserDao;
import com.itheima.mm.database.SqlSessionUtils;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

/**
 * 用户业务实现类
 */
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public User findUserByUsername(String username) {
        log.debug("UserServiceImpl...username:{}",username);
        // 调用Dao
        SqlSession sqlSession = SqlSessionUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.selectUserByUsername(username);
        log.debug("user:{}---",user);
        return user;
    }
}
