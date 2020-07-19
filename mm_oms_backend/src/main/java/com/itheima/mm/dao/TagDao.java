package com.itheima.mm.dao;

import com.itheima.mm.pojo.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：
 */
public interface TagDao {
    /**
     * 根据学科ID，获取标签列表
     * @param id
     * @return
     */
    List<Tag> selectTagListByCourseId(Integer id);

    /**
     * 根据题目ID，删除题目标签关系
     * @param questionId
     */
    void deleteTagListByQuestionId(Integer questionId);

    /**
     * 新增题目标签关系
     * @param map
     */
    void addQuestionTag(Map map);
}
