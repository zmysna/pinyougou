package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {


    /**
     * 引用服务接口代理对象
     * timeout: 调用服务接口方法超时时间毫秒数
     */
    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;

    /**
     * 添加类型模板
     */
    @PostMapping("/insert")
    public void insert(@RequestBody TypeTemplate typeTemplate) {
        typeTemplateService.insert(typeTemplate);
    }


    /**
     * 更新类型模板
     */
    @PostMapping("/update")
    public void update(@RequestBody TypeTemplate typeTemplate) {
        typeTemplateService.update(typeTemplate);
    }

    /**
     * 删除类型模板
     */
    @GetMapping("/delete")
    public void delete(Long[] ids) {
        if(ids != null && ids.length > 0){
            typeTemplateService.delete(ids);
        }
    }

    /** 多条件分页查询规格 */
    @GetMapping("/findByPage")
    public PageResult<TypeTemplate> findByPage(String name, Integer pageNum, Integer pageSize) {
        // 对变量name进行编码格式的转换
        if (name != null && StringUtils.isNotBlank(name)) {
            try {
                name = new String(name.getBytes("iso8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        PageResult pageResult = typeTemplateService.findBypage(name, pageNum, pageSize);
        return pageResult;
    }

}
