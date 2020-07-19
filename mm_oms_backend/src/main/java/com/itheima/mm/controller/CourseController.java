package com.itheima.mm.controller;

import com.itheima.framework.annotation.HmComponent;
import com.itheima.framework.annotation.HmRequestMapping;
import com.itheima.framework.annotation.HmSetter;
import com.itheima.mm.base.BaseController;
import com.itheima.mm.common.GlobalConst;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.service.UserService;
import com.itheima.mm.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import javax.management.relation.RelationSupport;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/13
 * @description ：
 */
@HmComponent
@Slf4j
public class CourseController extends BaseController {

    @HmSetter("courseService")
    private CourseService courseService;

    @HmRequestMapping("/course/add")
    public void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // 封装客户数据到POJO对象
            Course course = parseJSON2Object(request, Course.class);
            // 设置创建时间
            course.setCreateDate(DateUtils.parseDate2String(new Date()));
            // 获取用户ID
            User user = getSessionUser(request, GlobalConst.SESSION_KEY_USER);
            course.setUserId(user != null ? user.getId() : 1);
            // course.setUserId(user.getId());
            log.debug("表单数据:{}", course);
            // 调用Service
            courseService.addCourse(course);
            printResult(response, new Result(true, "新增成功"));

        } catch (RuntimeException e) {
            log.error("", e);
            printResult(response, new Result(false, "新增学科失败"));
        }
    }

    @HmRequestMapping("/course/findListByPage")
    public void findListByPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // 获取查询参数
            QueryPageBean queryPageBean = parseJSON2Object(request, QueryPageBean.class);
            log.debug("queryPageBean:{}", queryPageBean);
            if (queryPageBean == null) {
                queryPageBean = new QueryPageBean();
                queryPageBean.setCurrentPage(1);
                queryPageBean.setPageSize(10);
            }
            // 调用Service，获取PageResult
            //PageResult pageResult = new PageResult(33L, new ArrayList());
            PageResult pageResult = courseService.findListByPage(queryPageBean);
            printResult(response, new Result(true, "获取成功", pageResult));
        } catch (RuntimeException e) {
            log.error("", e);
            printResult(response, new Result(false, "获取学科列表失败"));
        }
    }

    @HmRequestMapping("/course/update")
    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // 封装客户数据到POJO对象
            Course course = parseJSON2Object(request, Course.class);
            log.debug("表单数据:{}", course);
            // 调用Service
            courseService.updateCourse(course);
            // 返回Json
            printResult(response, new Result(true, "修改成功"));
        } catch (RuntimeException e) {
            e.printStackTrace();
            printResult(response, new Result(false, "修改学科失败"));
        }
    }

    @HmRequestMapping("/course/findListAll")
    public void findListForQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // 调用Service
            List<Course> courseList = courseService.findListAll();
            // 响应JSON
            printResult(response, new Result(true, "查询成功", courseList));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
