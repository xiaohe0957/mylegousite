package cn.he.Service;

import cn.he.domain.tb_specification;
import cn.he.group.Specification;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SpecificationService {

    PageInfo<tb_specification> findPage(int pageNum, int pageSize);

    void save(Specification specification);


    Specification findOne(int id);

    void update(Specification specification);

    void delete(int[] ids);

    List<tb_specification> findAll();
}
