package com.itheima.mm.dao;

import com.itheima.mm.pojo.Catalog;

import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：
 */
public interface CatalogDao {
    List<Catalog> selectCatalogListByCourseId(Integer id);
}
