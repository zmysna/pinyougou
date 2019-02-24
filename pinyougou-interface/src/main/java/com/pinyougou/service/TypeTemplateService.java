package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.TypeTemplate;

/**
 * 类型模板服务层接口
 */
public interface TypeTemplateService {


    /**
     * 通过类型模板名称进行分页查询
     * @param name 类型模板名称
     * @param pageNum 当前页
     * @param pageSize  页的记录数
     * @return 返回结果，含有分页的数据和数据库中总记录数
     */
    PageResult<TypeTemplate> findBypage(String name, Integer pageNum, Integer pageSize);


    /**
     * 添加类型模板
     */
    void insert(TypeTemplate typeTemplate);

    /**
     * 更新类型模板
     * @param typeTemplate
     */
    void update(TypeTemplate typeTemplate);

    /**
     * 根据ids删除类型模板
     * @param ids
     */
    void delete(Long[] ids);
}
