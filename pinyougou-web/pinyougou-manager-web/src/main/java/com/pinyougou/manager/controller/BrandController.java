package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


/**
 * 品牌控制层
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    /**
     * 引用服务接口代理对象
     * timeout: 调用服务接口方法超时时间毫秒数
     */
    @Reference(timeout = 10000)
    private BrandService brandService;

    @GetMapping("/findAll")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    /**
     * 添加品牌
     */
    @PostMapping("/insert")
    public void insert(@RequestBody Brand brand) {
        brandService.insertBrand(brand);
    }


    /**
     * 更新品牌
     */
    @PostMapping("/update")
    public void update(@RequestBody Brand brand) {
        brandService.updateBrand(brand);
    }

    /**
     * 删除品牌
     */
    @GetMapping("/delete")
    public void delete(Long[] ids) {
        brandService.deleteBrand(ids);
    }


    /**
     * 多条件分页查询品牌
     */
    @GetMapping("/findByPage")
    public PageResult<Brand> findByPage(Brand brand, Integer pageNum, Integer pageSize) {
        if (brand != null && StringUtils.isNotBlank(brand.getName())) {
            try {
                brand.setName(new String(brand.getName().getBytes("iso8859-1"), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        PageResult<Brand> pageResult = brandService.findBypage(brand, pageNum, pageSize);
        return pageResult;
    }

    /** 查询所有的品牌*/
    @GetMapping("/findBrandList")
    public List<Map<String,Object>> findBrandList(){
        List<Map<String,Object>> mapList = brandService.findAllByIdAndName();
        return mapList;
    }
}
