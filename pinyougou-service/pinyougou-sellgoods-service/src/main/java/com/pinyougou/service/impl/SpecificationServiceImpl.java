package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 规格服务层实现类
 *
 */
@Service(interfaceName="com.pinyougou.service.SpecificationService")
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 单条件分页查询规格
     * @param specName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<Specification> findBypage(String specName, Integer pageNum, Integer pageSize) {
        PageInfo<Specification> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                specificationMapper.selectAllBySpecName(specName);
            }
        });
        return new PageResult<Specification>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 添加规格和规格选项
     * @param specification
     */
    @Override
    public void insert(Specification specification) {
        int specId = specificationMapper.insertSelective(specification);
        specificationOptionMapper.save(specification);
    }

    /**
     * 更新规格和规格选项
     * @param specification
     */
    @Override
    public void update(Specification specification) {
        specificationMapper.updateByPrimaryKeySelective(specification);
        //根据specId删除规格选项
        specificationOptionMapper.deleteBySpecId(specification.getId());
        //插入规格选项
        specificationOptionMapper.save(specification);
    }

    /**
     * 删除规格
     */
    @Override
    public void delete(Long[] ids) {
        //删除多个规格
        specificationMapper.deleteByIds(ids);
        //删除相应的规格选项
        for (Long id : ids) {
            specificationOptionMapper.deleteBySpecId(id);
        }
    }



}
