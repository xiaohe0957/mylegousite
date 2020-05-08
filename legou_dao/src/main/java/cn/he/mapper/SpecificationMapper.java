package cn.he.mapper;

import cn.he.domain.tb_specification;
import cn.he.group.Specification;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SpecificationMapper {
    @Select("select * from tb_specification ")
    List<tb_specification> findAll();

    @Insert("insert into tb_specification (spec_name) values (#{specName})")
    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    void save(tb_specification tb_specification);

    @Select("select * from tb_specification where id = #{id}")
    tb_specification findOne(int id);

    @Update("update tb_specification set spec_name = #{specName} where id = #{id}")
    void update(tb_specification tb_specification);

    @Delete("delete from tb_specification where id = #{id}")
    void delete(int id);
}
