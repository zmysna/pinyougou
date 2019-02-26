package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/24 16:50
 */
@Service(interfaceName = "com.pinyougou.service.ItemCatService")
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 根据父Id查询所有的商品分类
     */
    @Override
    public List<ItemCat> findAllByParentId(Long parentId) {
        return itemCatMapper.findAllByParentId(parentId);
    }

}
