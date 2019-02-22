package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationOptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 规格选项控制层
 */
@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {

    @Reference(timeout = 10000)
    private SpecificationOptionService specificationOptionService;

    /**
     * 通过规格ID查找规格选项
     * @param specId
     */
    @GetMapping("findOptionsBySpecId")
    public List<SpecificationOption> findOptionsBySpecId(@RequestParam(name = "id")Long specId) {
        List<SpecificationOption> options = specificationOptionService.findOptionsBySpecId(specId);
        return options;
    }



}
