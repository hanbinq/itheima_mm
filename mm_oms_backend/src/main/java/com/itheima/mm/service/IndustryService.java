package com.itheima.mm.service;

import com.itheima.mm.pojo.Industry;

import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：行业方向业务接口
 */
public interface IndustryService {
    /**
     * 获取所有数据
     * @return
     */
    List<Industry> findListAll();
}
