package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    /** 查询所有品牌 */
    List<Brand> findAll();

    /** 修改品牌*/
    void updateBrand(Brand brand);

    /** 删除品牌*/
    void deleteBrand(Long[] ids);

    /** 添加品牌 */
    void insertBrand(Brand brand);

    /** 多条件分页查询品牌 */
    PageResult findBypage(Brand brand, Integer pageNum, Integer pageSize);

    /** 查询所有的品牌( id 和 name )*/
    List<Map<String,Object>> findAllByIdAndName();
}

