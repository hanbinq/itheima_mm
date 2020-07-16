package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;

import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/15
 * @description ：
 */
public interface CourseDao {
    /**
     * 插入数据
     * @param course
     * @return
     */
    Integer insertCourse(Course course);

    List<Course> selectListByPage(QueryPageBean queryPageBean);

    Long selectCount(QueryPageBean queryPageBean);

    /**
     * 修改学科
     * @param course
     */
    void updateCourse(Course course);
}
