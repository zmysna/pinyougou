package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.ItemSearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商品搜索的控制层
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/3/4 11:01
 */
@RestController
public class ItemSearchController {

    @Reference(timeout = 10000)
    private ItemSearchService itemSearchService;

    /**
     * 搜索商品
     * @param params 搜索参数
     * @return
     */
    @PostMapping("/Search")
    public Map<String, Object> search(@RequestBody Map<String, Object> params) {
        Map<String, Object> search = itemSearchService.search(params);
        return search;
    }
}
