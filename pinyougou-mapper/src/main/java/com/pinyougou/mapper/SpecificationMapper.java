package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 规格数据访问层接口
 */
public interface SpecificationMapper extends Mapper<Specification> {

    /** 根据规格名称（如果有），查询所有规格*/
    List<Specification> selectAllBySpecName(String specName);

    /** 根据ID删除一个或多个规格*/
    void deleteByIds(@Param("ids") Long[] ids);

    /** 查询所有规格（id和name)*/
    @Select("select id,spec_name as text from tb_specification")
    List<Map<String,Object>> findAllByIdAndName();
}
