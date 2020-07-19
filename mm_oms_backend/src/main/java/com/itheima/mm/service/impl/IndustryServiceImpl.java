package com.itheima.mm.service.impl;

import com.itheima.framework.annotation.HmComponent;
import com.itheima.mm.base.BaseService;
import com.itheima.mm.dao.IndustryDao;
import com.itheima.mm.database.MmDaoException;
import com.itheima.mm.pojo.Industry;
import com.itheima.mm.service.IndustryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：行业方向业务实现类
 */
@HmComponent("industryService")
@Slf4j
public class IndustryServiceImpl extends BaseService implements IndustryService {
    @Override
    public List<Industry> findListAll() {
        try{
            // 调用Dao
            SqlSession session = getSession();
            IndustryDao industryDao = getDao(session, IndustryDao.class);
            List<Industry> industryList = industryDao.selectListAll();
            session.close();
            return industryList;
        }catch (MmDaoException e){
            e.printStackTrace();
            log.error("",e);
            throw new MmDaoException(e.getMessage());
        }
    }
}
