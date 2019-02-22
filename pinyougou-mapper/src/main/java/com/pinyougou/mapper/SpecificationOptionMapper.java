package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpecificationOptionMapper extends Mapper<SpecificationOption> {

    void save(Specification specification);

    /** 根据规格Id查找规格选项*/
    @Select("select * from tb_specification_option where spec_id = #{specId}")
    List<SpecificationOption> selectBySpecId(Long specId);


    /** 根据规格Id删除规格选项*/
    @Delete("delete from tb_specification_option where spec_id = #{specId}")
    void deleteBySpecId(Long specId);

}
