package com.pinyougou.mapper;

import com.pinyougou.pojo.ItemCat;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/24 16:52
 */
public interface ItemCatMapper extends Mapper<ItemCat> {


    /**
     * 根据父Id查询所有的商品分类
     */
    @Select("select * from tb_item_cat where parent_id = #{parentId}")
    List<ItemCat> findAllByParentId(Long parentId);
}
