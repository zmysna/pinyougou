package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 类型模板服务层实现类
 */
@Service(interfaceName = "com.pinyougou.service.TypeTemplateService")
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 通过类型模板名称进行分页查询
     * @param name 类型模板名称
     * @param pageNum 当前页
     * @param pageSize  页的记录数
     * @return 返回结果，含有分页的数据和数据库中总记录数
     */
    @Override
    public PageResult<TypeTemplate> findBypage(String name, Integer pageNum, Integer pageSize) {
        PageInfo<TypeTemplate> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(new ISelect() {
                    @Override
                    public void doSelect() {
                        typeTemplateMapper.selectAllByName(name);
                    }
                });
        return new PageResult<TypeTemplate>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 往类型模板表插入一条数据
     * @param typeTemplate
     */
    @Override
    public void insert(TypeTemplate typeTemplate) {
        typeTemplateMapper.insertSelective(typeTemplate);
    }

    /**
     * 根据id更新类型模板表的数据
     * @param typeTemplate
     */
    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
    }

    /**
     * 根据id删除类型模板
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        typeTemplateMapper.deleteByIds(ids);
    }

    /** 根据id查询一个类型模板*/
    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    /** 根据类型模板查询规格和规格选项*/
    @Override
    public List<Map> findSpecList(Long id) {
        TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
        String jsonStr = typeTemplate.getSpecIds();
        //[{"id":33,"text":"电视屏幕尺寸",options: []},{"id":43,"text":"内存大小",specificationOptions: []}]
        List<Map> maps = JSON.parseArray(jsonStr, Map.class);
        for (Map map : maps) {
            Integer specId = (Integer) map.get("id");
            List<SpecificationOption> options = specificationOptionMapper.selectBySpecId(specId.longValue());
            map.put("options",options);
        }
        return maps;
    }


}
