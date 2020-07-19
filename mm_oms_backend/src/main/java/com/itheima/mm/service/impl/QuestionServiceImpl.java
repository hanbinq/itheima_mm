package com.itheima.mm.service.impl;

import com.itheima.framework.annotation.HmComponent;
import com.itheima.mm.base.BaseService;
import com.itheima.mm.common.QuestionConst;
import com.itheima.mm.dao.CompanyDao;
import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.QuestionItemDao;
import com.itheima.mm.dao.TagDao;
import com.itheima.mm.database.MmDaoException;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.*;
import com.itheima.mm.service.QuestionService;
import com.itheima.mm.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

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
        if (queryPageBean.getQueryParams() == null) {
            Map map = new HashMap<>();
            map.put("isClassic", 0);
            queryPageBean.setQueryParams(map);
        }
        // 调用Dao
        try {
            SqlSession sqlSession = getSession();
            QuestionDao questionDao = getDao(sqlSession, QuestionDao.class);
            // 获取结果集
            List<Question> questionList = questionDao.selectListByPage(queryPageBean);
            // 获取记录数
            Long total = questionDao.selectCount(queryPageBean);
            sqlSession.close();
            return new PageResult(total, questionList);
        } catch (MmDaoException e) {
            e.printStackTrace();
            log.error("", e);
            throw new MmDaoException(e.getMessage());
        }
    }

    /**
     * 新增或更新试题
     *
     * @param question
     */
    @Override
    public void addOrUpdate(Question question) {
        SqlSession sqlSession = getSession();
        try {
            log.debug("Service addOrUpdate:{}", question);
            // 保存题目信息
            // 题目做初始化（isClass=0等）
            updateQuestion(sqlSession, question);
            // 保存题目选项信息
            updateQuestionItem(sqlSession, question);
            // 保存标签信息（题目与标签关系）
            updateQuestionTag(sqlSession, question);
            // 更新企业及行业信息
            updateCompany(sqlSession, question);
            commitAndCloseSession(sqlSession);
        } catch (MmDaoException e) {
            rollbackAndCloseSession(sqlSession);
            e.printStackTrace();
            log.error("", e);
            throw new MmDaoException(e.getMessage());
        }
    }

    // 新增或更新试题
    private void updateQuestion(SqlSession sqlSession, Question question) {
        question.setIsClassic(QuestionConst.ClassicStatus.CLASSIC_STATUS_NO.ordinal());
        question.setStatus(QuestionConst.Status.PRE_PUBLISH.ordinal());
        question.setReviewStatus(QuestionConst.ReviewStatus.PRE_REVIEW.ordinal());
        question.setCreateDate(DateUtils.parseDate2String(new Date()));

        QuestionDao questionDao = getDao(sqlSession, QuestionDao.class);
        if (question.getId() == 0) {
            questionDao.add(question);
            log.debug("新增成功:question_id：{}", question.getId());
        } else {
            questionDao.update(question);
            log.debug("更新成功");
        }
    }

    // 更新题目选项
    private void updateQuestionItem(SqlSession sqlSession, Question question) {
        QuestionItemDao questionItemDao = getDao(sqlSession, QuestionItemDao.class);
        // 遍历选项列表，逐一保存
        for (QuestionItem questionItem : question.getQuestionItemList()) {
            // 设置选项的QuestionId
            questionItem.setQuestionId(question.getId());
            // 过滤空选项
            if (questionItem.getContent() == null || questionItem.getContent().length() == 0) {
                if (questionItem.getImgUrl() == null || questionItem.getImgUrl().length() == 0) {
                    continue;
                }
            }

            // 判断是添加还是新增
            if (questionItem.getId() == 0) {
                questionItemDao.addQuestionItem(questionItem);
            } else {
                questionItemDao.updateQuestionItem(questionItem);
            }
        }
    }

    // 更新题目标签关系
    private void updateQuestionTag(SqlSession sqlSession, Question question) {
        TagDao tagDao = getDao(sqlSession, TagDao.class);
        // 删除旧关系
        tagDao.deleteTagListByQuestionId(question.getId());
        // 新增新关系
        for (Tag tag : question.getTagList()) {
            Map map = new HashMap();
            map.put("questionId", question.getId());
            map.put("tagId", tag.getId());
            tagDao.addQuestionTag(map);
        }
    }

    // 更新公司及行业信息
    private void updateCompany(SqlSession sqlSession, Question question) {
        // 更新公司信息
        CompanyDao companyDao = getDao(sqlSession, CompanyDao.class);
        Company company = question.getCompany();
        company.setUserId(question.getUserId());
        companyDao.updateCompanyCity(company);

        // 更新公司及行业关系，先删除旧关系
        companyDao.deleteCompanyIndustryByCompanyId(company.getId());
        // 新增企业及行业关系
        for (Industry industry : company.getIndustryList()) {
            Map map = new HashMap();
            map.put("companyId", company.getId());
            map.put("industryId", industry.getId());
            companyDao.addCompanyIndustry(map);
        }
    }
}
