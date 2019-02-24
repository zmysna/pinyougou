package com.pinyougou.service;

import com.pinyougou.pojo.SpecificationOption;

import java.util.List;

/***
 * 规格选项服务层接口
 */
public interface SpecificationOptionService {

    /**
     * 通过规格ID查找规格选项
     * @param specId
     * @return
     */
    List<SpecificationOption> findOptionsBySpecId(Long specId);

}
