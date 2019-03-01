package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {


    /**
     * 引用服务接口代理对象
     * timeout: 调用服务接口方法超时时间毫秒数
     */
    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;

    /** 根据id查询一个类型模板*/
    @GetMapping("/findOne")
    public TypeTemplate findOne(Long id){
        TypeTemplate typeTemplate = typeTemplateService.findOne(id);
        return typeTemplate;
    }

    /** 根据类型模板查找相应的规格和规格选项*/
    @GetMapping("/findSpecList")
    public List<Map> findSpecList(Long id){
        return typeTemplateService.findSpecList(id);
    }


}
