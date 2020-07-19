package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;

import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：题库Dao接口
 */
public interface QuestionDao {
    /**
     * 分页获取结果集
     * @param pageBean
     * @return
     */
    List<Question> selectListByPage(QueryPageBean pageBean);

    /**
     * 根据条件获取记录数
     * @param pageBean
     * @return
     */
    Long selectCount(QueryPageBean pageBean);

    /**
     * 新增题目
     * @param question
     */
    void add(Question question);

    /**
     * 更新题目
     * @param question
     */
    void update(Question question);
}
