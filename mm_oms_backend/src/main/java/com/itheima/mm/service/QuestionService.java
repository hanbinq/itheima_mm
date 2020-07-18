package com.itheima.mm.service;

import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import org.apache.ibatis.annotations.Param;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：题库业务接口
 */
public interface QuestionService {
    PageResult findListByPage(@Param("queryPageBean") QueryPageBean queryPageBean);
}
