package com.pinyougou.mapper;

import com.pinyougou.pojo.TypeTemplate;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 类型模板数据访问层接口
 */
public interface TypeTemplateMapper extends Mapper<TypeTemplate> {

    /** 根据类型模板名称（如果有）动态查询所有规格*/
    List<TypeTemplate> selectAllByName(String name);

    /**
     * 根据ids删除类型模板
     * @param ids
     */
    void deleteByIds(@Param("ids") Long[] ids);
}
