package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Specification;

/**
 * 规格服务层接口
 */
public interface SpecificationService {

    /** 根据规格名称（或者无规格名称）分页查询规格 */
    PageResult<Specification> findBypage(String specName, Integer pageNum, Integer pageSize);

    /** 插入规格*/
    void insert(Specification specification);

    /** 更新规格*/
    void update(Specification specification);

    /** 删除规格*/
    void delete(Long[] ids);

}
