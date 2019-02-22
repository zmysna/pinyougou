package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 规格选项服务层实现类
 */
@Service(interfaceName = "com.pinyougou.service.SpecificationOptionService")
@Transactional
public class SpecificationOptionServiceImpl implements SpecificationOptionService {


    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;


    /** 通过规格Id查找规格选项*/
    @Override
    public List<SpecificationOption> findOptionsBySpecId( Long specId) {
        List<SpecificationOption> specificationOptions = specificationOptionMapper.selectBySpecId(specId);
        return specificationOptions;
    }



}
