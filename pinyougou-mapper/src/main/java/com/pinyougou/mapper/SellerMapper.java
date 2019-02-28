package com.pinyougou.mapper;

import com.pinyougou.pojo.Seller;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/26 20:43
 */

public interface SellerMapper extends Mapper<Seller> {

    /** 多条件查询商家*/
    List<Seller> findAll(Seller seller);
}
