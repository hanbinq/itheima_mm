package com.itheima.mm.controller;

import com.itheima.framework.annotation.HmComponent;
import com.itheima.framework.annotation.HmRequestMapping;
import com.itheima.framework.annotation.HmSetter;
import com.itheima.mm.base.BaseController;
import com.itheima.mm.common.GlobalConst;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.QuestionService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：题库控制器
 */
@HmComponent
@Slf4j
public class QuestionController extends BaseController {

    @HmSetter("questionService")
    private QuestionService questionService;

    /**
     * 分页获取基础题目列表
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @HmRequestMapping("/question/findListByPage")
    public void findListByPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            QueryPageBean queryPageBean = parseJSON2Object(request, QueryPageBean.class);
            log.debug("queryBean:{}", queryPageBean);
            if (queryPageBean == null) {
                queryPageBean = new QueryPageBean();
                queryPageBean.setPageSize(10);
                queryPageBean.setCurrentPage(1);
            }

            // 调用Service，获取PageResult
            PageResult listByPage = questionService.findListByPage(queryPageBean);
            //响应JSON
            printResult(response, new Result(true, "查询成功", listByPage));
        } catch (RuntimeException e) {
            e.printStackTrace();
            printResult(response, new Result(false, "查询失败"));
        }
    }

    @HmRequestMapping("/question/addOrUpdate")
    public void addOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // 获取表单数据
            Question question = parseJSON2Object(request, Question.class);
            log.debug("调用Service参数question:{}", question);
            // 设置用户id
            User user = getSessionUser(request, GlobalConst.SESSION_KEY_USER);
            question.setUserId(user != null ? user.getId() : 1);
            // 调用Service
            questionService.addOrUpdate(question);
            // 响应JSON
            printResult(response, new Result(true, "更新成功"));
        } catch (RuntimeException e) {
            e.printStackTrace();
            printResult(response, new Result(false, "更新失败"));
        }
    }
}
