package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceName="com.pinyougou.service.BrandService")
@Transactional
public class BrandServcieImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {

        /*PageInfo<Brand> pageInfo = PageHelper.startPage(1, 5)
                .doSelectPageInfo(new ISelect() {
                    @Override
                    public void doSelect() {
                        brandMapper.selectAll();
                    }
                });
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("总页数：" + pageInfo.getPages());
        return pageInfo.getList();*/
        return brandMapper.selectAll();
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void deleteBrand(Long[] ids) {
        brandMapper.deleteByIds(ids);
    }

    @Override
    public void insertBrand(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public PageResult findBypage(Brand brand, Integer pageNum, Integer pageSize) {
        PageInfo<Brand> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                brandMapper.selectByBrand(brand);
            }
        });
        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());
    }



}
