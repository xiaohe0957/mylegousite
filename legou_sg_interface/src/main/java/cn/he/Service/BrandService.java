package cn.he.Service;

import cn.he.domain.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    List<Brand> findAll();

    PageInfo<Brand> findPage(int num, int size);

    void save(Brand brand);

    Brand findOne(int id);

    void update(Brand brand);

    void delete(int[] ids);
}
