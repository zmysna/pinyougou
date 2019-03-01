package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;
import com.pinyougou.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 商品控制器
 *
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/28 11:11
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference(timeout = 10000)
    private GoodsService goodsService;

    /**
     * 插入一个SPU商品，并向相关的表插入数据
     *
     * @param goods
     * @return
     */
    @PostMapping("/insert")
    public boolean insert(@RequestBody Goods goods) {
        try {
            /** 获取登录用户名 */
            String sellerId = SecurityContextHolder.getContext()
                    .getAuthentication().getName();
            /** 设置商家ID */
            goods.setSellerId(sellerId);
            goodsService.save(goods);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 多条件分页查询
     */
    @GetMapping("/findByPage")
    public PageResult findByPage(Goods goods, Integer pageNum, Integer pageSize) {
        PageResult pageResult = goodsService.findByPage(goods, pageNum, pageSize);
        return pageResult;
    }

    @GetMapping("/delete")
    public void delete(Long[] ids) {
        goodsService.deleteByIds(ids);
    }

    /** 商品上下架*/
    @GetMapping("/updateMarktable")
    public void updateMarktable(Long[] ids, String status) {
        goodsService.updateMarktable(ids,status);
    }
}
