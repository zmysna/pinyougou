package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.Item;
import com.pinyougou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品服务层实现类
 *
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/28 11:36
 */
@Service(interfaceName = "com.pinyougou.service.GoodsService")
@Transactional(rollbackFor = RuntimeException.class)
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public void save(Goods goods) {
        try {
            // 设置未审核状态
            goods.setAuditStatus("0");
            goodsMapper.insertSelective(goods);
            // 为商品描述对象设置主键id
            goods.getGoodsDesc().setGoodsId(goods.getId());
            goodsDescMapper.insertSelective(goods.getGoodsDesc());
            // 判断是否启用规格
            if ("1".equals(goods.getIsEnableSpec())) {
                /**迭代所有的SKU具体商品集合，往SKU表插入数据 */
                for (Item item : goods.getItems()) {
                    //设置标题
                    StringBuilder title = new StringBuilder(goods.getGoodsName());
                    Map map = JSON.parseObject(item.getSpec(), Map.class);
                    for (Object value : map.values()) {
                        title.append(" " + value);
                    }
                    item.setTitle(title.toString());
                    List<Map> imageList = JSON.parseArray(
                            goods.getGoodsDesc().getItemImages(), Map.class);
                    //设置图片地址，取第一张图片
                    if (imageList != null && imageList.size() > 0) {
                        item.setImage((String) imageList.get(0).get("url"));
                    }
                    //设置商品分类
                    item.setCategoryid(goods.getCategory3Id());
                    //设置时间
                    item.setCreateTime(new Date());
                    item.setUpdateTime(new Date());
                    //设置商品编号
                    item.setGoodsId(goods.getId());
                    //设置商家编号
                    item.setSellerId(goods.getSellerId());
                    //设置商品分类名称
                    item.setCategory(itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName());
                    //设置品牌名称
                    item.setBrand(brandMapper.selectByPrimaryKey(goods.getBrandId()).getName());
                    //设置商家名称
                    item.setSeller(sellerMapper.selectByPrimaryKey(goods.getSellerId()).getNickName());
                    itemMapper.insertSelective(item);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("保存出错");
        }
    }

    /**
     * 多条件分页查询
     */
    @Override
    public PageResult findByPage(Goods goods, Integer pageNum, Integer pageSize) {
        PageInfo<Map> pageInfo = PageHelper.startPage(pageNum,pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                goodsMapper.findAll(goods);
            }
        });
        //插入categoryName
        List<Map> dataList = pageInfo.getList();
        for (Map<String, Object> map : dataList) {
            map.put("category1Name", itemCatMapper.selectByPrimaryKey(map.get("category1_id")).getName());
            map.put("category2Name", itemCatMapper.selectByPrimaryKey(map.get("category2_id")).getName());
            map.put("category3Name", itemCatMapper.selectByPrimaryKey(map.get("category3_id")).getName());
        }
        return new PageResult(pageInfo.getTotal(),dataList);
    }

    /** 删除商品（设置删除标志）*/
    @Override
    public void deleteByIds(Long[] ids) {
        goodsMapper.updateDeleteStatus(ids);
    }

    @Override
    public void updateMarktable(Long[] ids, String status) {
        goodsMapper.updateMarktable(ids,status);
    }

}
