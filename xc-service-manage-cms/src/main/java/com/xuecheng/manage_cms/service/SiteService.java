package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;

import com.xuecheng.framework.domain.cms.request.QuerySiteRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {

    @Autowired
    CmsSiteRepository cmsSiteRepository;


    /**
     * 站点分页查询

     * @param querySiteRequest  查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList( QuerySiteRequest querySiteRequest) {
        //条件匹配器
        //页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("SiteName", ExampleMatcher.GenericPropertyMatchers.contains());
        //条件值
        CmsSite cmsSite = new CmsSite();
        //站点ID为查询条件
        if (StringUtils.isNotEmpty(querySiteRequest.get_Id())) {
            cmsSite.setSiteId(querySiteRequest.get_Id());
        }
        //页面别名为查询条件
        if (StringUtils.isNotEmpty(querySiteRequest.getSiteName())) {
            cmsSite.setSiteName(querySiteRequest.getSiteName());
        }
        //创建条件实例
        Example<CmsSite> example = Example.of(cmsSite, exampleMatcher);


        //分页查询
        List<CmsSite> all = cmsSiteRepository.findAll();
        QueryResult<CmsSite> cmsSiteQueryResult = new QueryResult<CmsSite>();
        cmsSiteQueryResult.setList(all);
        cmsSiteQueryResult.setTotal(all.size());
        //返回结果
        return new QueryResponseResult(CommonCode.SUCCESS, cmsSiteQueryResult);

        }

}
