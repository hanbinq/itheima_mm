package com.itheima.mm.dao;

import com.itheima.mm.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Dao接口
 */
public interface UserDao {
    /**
     * 根据用户名获取用户对象
     * @param username
     * @return
     */
    User selectUserByUsername(@Param("username") String username);
}
