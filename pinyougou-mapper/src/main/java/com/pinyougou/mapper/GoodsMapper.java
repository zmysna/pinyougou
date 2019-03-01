package com.pinyougou.mapper;

import com.pinyougou.pojo.Goods;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/3/1 11:04
 */
public interface GoodsMapper extends Mapper<Goods> {

    /** 多条件动态查询合适的商品*/
    List<Map<String,Object>> findAll(Goods goods);

    /** 删除商品（设置删除状态）*/
    void updateDeleteStatus(@Param("ids") Long[] ids);

    /** 商品上下架*/
    void updateMarktable(@Param("ids") Long[] ids, @Param("status") String status);
}
