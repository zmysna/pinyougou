package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商家的控制器
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/26 20:33
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference(timeout = 10000)
    private SellerService sellerService;

    /** 注册服务（添加一个商家）*/
    @PostMapping("/insert")
    public boolean insert(@RequestBody Seller seller) {
        try {
            //对密码字段进行加密
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            passwordEncoder.encode(seller.getPassword());
            //插入数据库
            sellerService.insert(seller);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
