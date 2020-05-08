package cn.he.Service.Impl;

import cn.he.domain.Brand;
import cn.he.Service.BrandService;
import cn.he.mapper.BrandMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /*
    * 查询所有
    * */
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

    /*
    * 分页查询
    * */
    @Override
    public PageInfo<Brand> findPage(int num, int size) {
        PageHelper.startPage(num,size);
        List<Brand> list = brandMapper.findAll();
        PageInfo<Brand> page = new PageInfo<>(list);
        return page;
    }

    /*
    * 保存
    * */
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }

    @Override
    public Brand findOne(int id) {
        return brandMapper.findOne(id);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    @Override
    public void delete(int[] ids) {
        for (int id : ids) {
            brandMapper.delete(id);
        }
    }
}
