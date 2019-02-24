package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Specification;
import com.pinyougou.service.SpecificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 规格控制层
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {


    /**
     * 引用服务接口代理对象
     * timeout: 调用服务接口方法超时时间毫秒数
     */
    @Reference(timeout = 10000)
    private SpecificationService specificationService;

//
//    @GetMapping("/findAll")
//    public List<Specification> findAll() {
//        return specificationService.findAll();
//    }
//

    /**
     * 添加规格和规格选项
     */
    @PostMapping("/insert")
    public void insert(@RequestBody Specification specification) {
        specificationService.insert(specification);
    }


    /**
     * 更新规格
     */
    @PostMapping("/update")
    public void update(@RequestBody Specification specification) {
        this.specificationService.update(specification);
    }

    /**
     * 删除规格
     */
    @GetMapping("/delete")
    public void delete(Long[] ids) {
        specificationService.delete(ids);
    }

    /**
     * 多条件分页查询规格
     */
    @GetMapping("/findByPage")
    public PageResult<Specification> findByPage(String specName, Integer pageNum, Integer pageSize) {
        if (specName != null && StringUtils.isNotBlank(specName)) {
            try {
                specName = new String(specName.getBytes("iso8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        PageResult<Specification> pageResult = specificationService.findBypage(specName, pageNum, pageSize);
        return pageResult;
    }

    /**
     * 查询所有规格
     */
    @GetMapping("/findSpecificationList")
    public List<Map<String, Object>> findSpecificationList() {
        return specificationService.findAllByIdAndName();
    }

}
