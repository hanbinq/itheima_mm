package com.itheima.mm.service;

import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/15
 * @description ：
 */
public interface CourseService {
    /**
     * 添加学科
     * @param course
     */
    void addCourse(Course course);

    /**
     * 根据条件分页获取数据
     * @param queryPageBean
     * @return
     */
    PageResult findListByPage(QueryPageBean queryPageBean);

    /**
     * 更新学科
     * @param course
     */
    void updateCourse(Course course);
}
