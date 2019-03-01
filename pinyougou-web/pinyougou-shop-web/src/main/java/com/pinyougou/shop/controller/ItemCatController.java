package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类控制层
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/24 16:26
 */

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference(timeout = 10000)
    private ItemCatService itemCatService;

    /**
     * 根据parentId查询所有商品分类
     */
    @GetMapping("/findAllByParentId")
    public List<ItemCat> findAllByParentId(Long parentId){
        List<ItemCat> list = itemCatService.findAllByParentId(parentId);
        return list;
    }

}
