package com.itheima.mm.service.impl;

import com.itheima.framework.annotation.HmComponent;
import com.itheima.mm.base.BaseService;
import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.database.MmDaoException;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：题库业务实现类
 */
@HmComponent("questionService")
@Slf4j
public class QuestionServiceImpl extends BaseService implements QuestionService {
    @Override
    public PageResult findListByPage(QueryPageBean queryPageBean) {
        log.debug("QuestionServiceImpl-queryPageBean:{}", queryPageBean);
        if (queryPageBean.getQueryParams() == null){
            Map map = new HashMap<>();
            map.put("isClassic", 0);
            queryPageBean.setQueryParams(map);
        }
        // 调用Dao
        try{
            SqlSession sqlSession = getSession();
            QuestionDao questionDao = getDao(sqlSession, QuestionDao.class);
            // 获取结果集
            List<Question> questionList = questionDao.selectListByPage(queryPageBean);
            // 获取记录数
            Long total = questionDao.selectCount(queryPageBean);
            sqlSession.close();
            return new PageResult(total, questionList);
        }catch (MmDaoException e){
            e.printStackTrace();
            log.error("",e);
            throw new MmDaoException(e.getMessage());
        }
    }
}
