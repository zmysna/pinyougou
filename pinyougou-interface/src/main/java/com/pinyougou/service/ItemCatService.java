package com.pinyougou.service;

import com.pinyougou.pojo.ItemCat;

import java.util.List;

/**
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/24 16:35
 */
public interface ItemCatService {
    /**
     * 根据父Id查询所有的商品分类
     */
    List<ItemCat> findAllByParentId(Long parentId);
}
