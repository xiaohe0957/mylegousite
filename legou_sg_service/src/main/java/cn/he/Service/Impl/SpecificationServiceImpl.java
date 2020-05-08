package cn.he.Service.Impl;


import cn.he.Service.SpecificationService;
import cn.he.domain.tb_specification;
import cn.he.domain.tb_specification_option;
import cn.he.group.Specification;
import cn.he.mapper.SpecificationMapper;
import cn.he.mapper.SpecificationOptionMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@Service(interfaceClass = SpecificationService.class)
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;


    @Override
    public PageInfo<tb_specification>
    findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<tb_specification> list = specificationMapper.findAll();
        return new PageInfo<>(list);
    }

    @Override
    public void save(Specification specification) {
        tb_specification tbSpecification = specification.getTbSpecification();
        List<tb_specification_option> tbSpecificationOptionList = specification.getTbSpecificationOptionList();
        specificationMapper.save(tbSpecification);
        Integer id = tbSpecification.getId();
        for (tb_specification_option tb_specification_option : tbSpecificationOptionList) {
            tb_specification_option.setSpecId(id);
            specificationOptionMapper.save(tb_specification_option);
        }
    }

    @Override
    public Specification findOne(int id) {
        tb_specification tb_specification = specificationMapper.findOne(id);
        List<tb_specification_option> list = specificationOptionMapper.findOne(id);
        Specification specification = new Specification();
        specification.setTbSpecification(tb_specification);
        specification.setTbSpecificationOptionList(list);
        return specification;
    }

    @Override
    public void update(Specification specification) {
        tb_specification tbSpecification = specification.getTbSpecification();
        Integer id = tbSpecification.getId();
        List<tb_specification_option> tbSpecificationOptionList = specification.getTbSpecificationOptionList();
        specificationMapper.update(tbSpecification);
        specificationOptionMapper.delete(id);
        for (tb_specification_option tb_specification_option : tbSpecificationOptionList) {
            specificationOptionMapper.save(tb_specification_option);
        }
    }

    @Override
    public void delete(int[] ids) {
        for (int id : ids) {
            specificationMapper.delete(id);
            specificationOptionMapper.delete(id);
        }
    }

    @Override
    public List<tb_specification> findAll() {
        return specificationMapper.findAll();
    }

}
