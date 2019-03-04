package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.service.ItemSearchService;
import com.pinyougou.solr.SolrItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品搜素服务层实现类
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/3/4 10:35
 */
@Service(interfaceName = "com.pinyougou.service.ItemSearchService")
@Transactional(rollbackFor = RuntimeException.class)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 搜索商品
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> search(Map<String, Object> params) {
        Map<String,Object> map = new HashMap<>();
        SimpleQuery query = new SimpleQuery("*:*");
        String keywords = (String) params.get("keywords");
        if(StringUtils.isNotBlank(keywords)){
            Criteria criteria = new Criteria("keywords").is(keywords);
            query.addCriteria(criteria);
        }
        ScoredPage<SolrItem> solrItems = solrTemplate.queryForPage(query, SolrItem.class);
        map.put("rows",solrItems.getContent());
        return map;
    }
}
