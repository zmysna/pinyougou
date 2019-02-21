package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;

import javax.naming.ldap.PagedResultsControl;
import java.awt.print.Pageable;
import java.util.List;

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
}

