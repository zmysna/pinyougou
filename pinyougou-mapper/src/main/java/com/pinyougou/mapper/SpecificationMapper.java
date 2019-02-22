package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpecificationMapper extends Mapper<Specification> {

    /** 根据规格名称（如果有），查询所有规格*/
    List<Specification> selectAllBySpecName(String specName);

    /** 根据ID删除一个或多个规格*/
    void deleteByIds(@Param("ids") Long[] ids);
}
