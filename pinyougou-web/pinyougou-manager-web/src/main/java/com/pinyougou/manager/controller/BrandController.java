package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class BrandController {


    /**
     * 引用服务接口代理对象
     * timeout: 调用服务接口方法超时时间毫秒数
     */
    @Reference(timeout = 10000)
    private BrandService brandService;

    @GetMapping("/brand/findAll")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    /**
     * 添加品牌
     */
    @PostMapping("/brand/insert")
    public void insert(@RequestBody Brand brand) {
        brandService.insertBrand(brand);
    }


    /**
     * 更新品牌
     */
    @PostMapping("/brand/update")
    public void update(@RequestBody Brand brand) {
        brandService.updateBrand(brand);
    }

    /**
     * 删除品牌
     */
    @PostMapping("/brand/delete")
    public void delete(Long[] ids) {
        brandService.deleteBrand(ids);
    }

    /** 多条件分页查询品牌 */
    @GetMapping("/brand/findByPage")
    public PageResult<Brand> findByPage(Brand brand, Integer pageNum, Integer pageSize) {
        if (brand != null && StringUtils.isNotBlank(brand.getName())) {
            try {
                brand.setName(new String(brand.getName().getBytes("iso8859-1"), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        PageResult<Brand> pageResult = brandService.findBypage(brand,pageNum, pageSize);
        return pageResult;
    }

}
