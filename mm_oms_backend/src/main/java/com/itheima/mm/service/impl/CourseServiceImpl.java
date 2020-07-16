package com.itheima.mm.service.impl;

import com.itheima.framework.annotation.HmComponent;
import com.itheima.mm.base.BaseService;
import com.itheima.mm.dao.CourseDao;
import com.itheima.mm.database.MmDaoException;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/15
 * @description ：学科业务实现类
 */
@HmComponent("courseService")
@Slf4j
public class CourseServiceImpl extends BaseService implements CourseService {
    @Override
    public void addCourse(Course course) {
        log.debug("course:{}", course);
        //调用Dao
        SqlSession sqlSession = getSession();

        try {
            CourseDao courseDao = getDao(sqlSession, CourseDao.class);
            Integer result = courseDao.insertCourse(course);
            if (result == 0) {
                throw new MmDaoException("添加失败");
            }
            // 提交事务
            commitAndCloseSession(sqlSession);
        } catch (MmDaoException e) {
            // 回滚事务
            rollbackAndCloseSession(sqlSession);
            e.printStackTrace();
            log.error("", e);
        }
    }

    @Override
    public PageResult findListByPage(QueryPageBean queryPageBean) {
        try {
            log.debug("queryPageBean:{}", queryPageBean);
            // Dao
            SqlSession sqlSession = getSession();
            CourseDao courseDao = getDao(sqlSession, CourseDao.class);
            // 获取结果集
            List<Course> courseList = courseDao.selectListByPage(queryPageBean);
            // 获取记录数
            Long total = courseDao.selectCount(queryPageBean);
            return new PageResult(total, courseList);
        } catch (MmDaoException e) {
            e.printStackTrace();
            log.error("", e);
            throw new MmDaoException(e.getMessage());
        }

    }

    @Override
    public void updateCourse(Course course) {
        SqlSession sqlSession = getSession();
        try{
            CourseDao courseDao = getDao(sqlSession, CourseDao.class);
            // 调用方法完成更新
            courseDao.updateCourse(course);
            commitAndCloseSession(sqlSession);
        }catch (MmDaoException e){
            e.printStackTrace();
            rollbackAndCloseSession(sqlSession);
            log.error("",e);
            throw new MmDaoException(e.getMessage());
        }
    }
}
