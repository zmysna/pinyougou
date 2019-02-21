package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {



    /*@Select("select * from tb_brand")
    List<Brand> findAll();*/

    void deleteByIds(@Param("ids") Long[] ids);

    List<Brand> selectByBrand(Brand brand);
}



