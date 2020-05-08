package cn.he.mapper;

import cn.he.domain.TbSeller;
import cn.he.domain.TbSellerExample;
import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbSellerMapper {
    int countByExample(TbSellerExample example);

    int deleteByExample(TbSellerExample example);

    int deleteByPrimaryKey(String sellerId);

    int insert(TbSeller record);

    int insertSelective(TbSeller record);

    List<TbSeller> selectByExample(TbSellerExample example);

    TbSeller selectByPrimaryKey(String sellerId);

    int updateByExampleSelective(@Param("record") TbSeller record, @Param("example") TbSellerExample example);

    int updateByExample(@Param("record") TbSeller record, @Param("example") TbSellerExample example);

    int updateByPrimaryKeySelective(TbSeller record);

    int updateByPrimaryKey(TbSeller record);

    @Select("select * from tb_seller where status = #{status}")
    List<TbSeller> searchAll(TbSeller tbSeller);

    @Select("select * from tb_seller where seller_id = #{sellerId}")
    TbSeller findOne(String sellerId);

    @Update("update tb_seller set status = #{status} WHERE seller_id = #{sellerId} ")
    void auditing(@Param("sellerId") String sellerId, @Param("status") String status);
}