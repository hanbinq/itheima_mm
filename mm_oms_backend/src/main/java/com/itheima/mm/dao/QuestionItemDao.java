package com.itheima.mm.dao;

import com.itheima.mm.pojo.QuestionItem;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/19
 * @description ：题目选项Dao接口
 */
public interface QuestionItemDao {
    /**
     * 新增题目选项
     * @param questionItem
     */
    void addQuestionItem(QuestionItem questionItem);

    /**
     * 更新题目选项
     * @param questionItem
     */
    void updateQuestionItem(QuestionItem questionItem);
}
