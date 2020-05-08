package cn.he.mapper;

import cn.he.domain.tb_specification_option;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SpecificationOptionMapper {
    @Insert("insert into tb_specification_option (option_name,spec_id,orders) values (#{optionName},#{specId},#{orders})")
    void save(tb_specification_option tb_specification_option);

    @Select("select * from tb_specification_option where spec_id = #{id}")
    List<tb_specification_option> findOne(int id);

    @Delete("delete from tb_specification_option where spec_id = #{id}")
    void delete(Integer id);
}
