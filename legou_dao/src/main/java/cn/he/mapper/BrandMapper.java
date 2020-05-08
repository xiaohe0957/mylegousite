package cn.he.mapper;

import cn.he.domain.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BrandMapper {
    List<Brand> findAll();

    @Insert("insert INTO tb_brand (name,first_char) VALUES (#{name},#{firstChar});")
    void save(Brand brand);

    @Select("select * from tb_brand where id = #{id}")
    Brand findOne(int id);

    @Select("select * from tb_brand where id = #{id}")
    Brand find(long id);

    @Update("update tb_brand set name=#{name},first_char=#{firstChar} where id=#{id}")
    void update(Brand brand);

    @Delete("delete from tb_brand where id = #{id}")
    void delete(int id);
}
