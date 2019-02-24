package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends Mapper<Brand> {



    @Select("select * from tb_brand")
    List<Brand> findAll();

    void deleteByIds(@Param("ids") Long[] ids);

    /** 多条件动态查询品牌*/
    List<Brand> selectByBrand(Brand brand);

    /** 查询所有的品牌（id和name）*/
    @Select("select id, name as text from tb_brand")
    List<Map<String,Object>> findAllByIdAndName();
}




