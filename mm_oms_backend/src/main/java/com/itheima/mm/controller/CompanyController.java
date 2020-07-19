package com.itheima.mm.controller;

import com.itheima.framework.annotation.HmComponent;
import com.itheima.framework.annotation.HmRequestMapping;
import com.itheima.framework.annotation.HmSetter;
import com.itheima.mm.base.BaseController;
import com.itheima.mm.base.BaseService;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Company;
import com.itheima.mm.service.CompanyService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ：jing.liang
 * @date ：Created in 2020/7/18
 * @description ：公司控制器
 */
@HmComponent
@Slf4j
public class CompanyController extends BaseController {

    @HmSetter("companyService")
    private CompanyService companyService;

    @HmRequestMapping("/company/findListAll")
    public void findListAll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // 调用Service
            List<Company> companyList = companyService.findListAll();
            // 响应JSON
            printResult(response, new Result(true, "查询成功", companyList));
        } catch (RuntimeException e) {
            e.printStackTrace();
            printResult(response, new Result(false, "查询失败"));
        }
    }
}
