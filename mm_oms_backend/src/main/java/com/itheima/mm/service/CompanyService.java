package com.itheima.mm.service;

import com.itheima.mm.pojo.Company;

import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：公司业务接口
 */
public interface CompanyService {
    /**
     * 获取所有公司
     * @return
     */
    List<Company> findListAll();
}
