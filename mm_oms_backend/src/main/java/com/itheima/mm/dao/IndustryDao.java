package com.itheima.mm.dao;

import com.itheima.mm.pojo.Industry;

import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：行业方向Dao
 */
public interface IndustryDao {
    /**
     * 获取所有行业方向
     * @return
     */
    List<Industry> selectListAll();
}
