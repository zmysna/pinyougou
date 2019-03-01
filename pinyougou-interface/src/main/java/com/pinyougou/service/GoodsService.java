package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;

/**
 * 商品服务层接口
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/28 11:34
 */
public interface GoodsService {

    /** 插入一个SPU商品，并向相关的表插入数据*/
    void save(Goods goods);

    /** 多条件分页查询*/
    PageResult findByPage(Goods goods, Integer pageNum, Integer pageSize);

    /** 删除商品*/
    void deleteByIds(Long[] ids);

    /** 商品上下架*/
    void updateMarktable(Long[] ids, String status);
}
