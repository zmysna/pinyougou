package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商家控制器
 *
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/26 21:24
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference(timeout = 10000)
    private SellerService sellerService;

    /**
     * 多条件分页查询商家
     */
    @GetMapping("/findByPage")
    public PageResult findByPage(Seller seller, Integer pageNum, Integer pageSize) {
        try {
            /** GET请求中文转码 */
            if (seller != null && StringUtils.isNoneBlank(seller.getName())) {
                seller.setName(new String(seller.getName()
                        .getBytes("ISO8859-1"), "UTF-8"));
            }
            if (seller != null && StringUtils.isNoneBlank(seller.getNickName())) {
                seller.setNickName(new String(seller.getNickName()
                        .getBytes("ISO8859-1"), "UTF-8"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sellerService.findByPage(seller, pageNum, pageSize);
    }

    /**
     * 审核商家（修改商家的状态）
     */
    @GetMapping("/updateStatus")
    public boolean updateStatus(String sellerId, String status) {
        try{
            sellerService.updateStatus(sellerId,status);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
