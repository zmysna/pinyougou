package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SellerMapper;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 商家服务层实现类
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/26 20:41
 */
@Service(interfaceName = "com.pinyougou.service.SellerService")
@Transactional(rollbackFor = RuntimeException.class)
public class SellerServiceImpl implements SellerService {

    @Autowired
    public SellerMapper sellerMapper;

    /** 添加一个商家*/
    @Override
    public void insert(Seller seller) {
        try {
            seller.setStatus("0");
            seller.setCreateTime(new Date());
            sellerMapper.insertSelective(seller);
        } catch (Exception e){
            throw new RuntimeException("出错了");
        }
    }

    /** 多条件分页查询商家*/
    @Override
    public PageResult findByPage(Seller seller, Integer pageNum, Integer pageSize) {
        PageInfo<Seller> pageInfo = PageHelper.startPage(pageNum,pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                sellerMapper.findAll(seller);
            }
        });
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 审核商家（修改商家的状态）
     */
    @Override
    public void updateStatus(String sellerId, String status) {
        try{
            Seller seller = new Seller();
            seller.setSellerId(sellerId);
            seller.setStatus(status);
            sellerMapper.updateByPrimaryKey(seller);
        } catch (Exception e) {
            throw new RuntimeException("更新商家出错");
        }
    }



}
