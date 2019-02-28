package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Seller;

/**
 * 商家服务层接口
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/26 20:36
 */

public interface SellerService {
    /** 添加一个商家*/
    void insert(Seller seller);

    /** 多条件分页查询商家*/
    PageResult findByPage(Seller seller, Integer pageNum, Integer pageSize);

    /**
     * 审核商家（修改商家的状态）
     */
    void updateStatus(String sellerId, String status);
}
