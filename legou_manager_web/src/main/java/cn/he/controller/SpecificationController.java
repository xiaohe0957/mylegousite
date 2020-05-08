package cn.he.controller;

import cn.he.Service.SpecificationService;
import cn.he.domain.Brand;
import cn.he.domain.tb_specification;
import cn.he.entity.result;
import cn.he.group.Specification;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Reference
    private SpecificationService specificationService;

    /*
    * 分页查询规格数据
    * */
    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public result findPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        try {
            PageInfo<tb_specification> pageInfo = specificationService.findPage(pageNum,pageSize);
            return new result(true,"成功",pageInfo.getTotal(),pageInfo.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

    /*
     * 新增规格数据
     * */
    @RequestMapping("/save")
    public result save(@RequestBody Specification specification){
        try {
            specificationService.save(specification);
            return new result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

    /*
     * 规格数据回显
     * */
    @RequestMapping("/findOne/{id}")
    public result findOne(@PathVariable("id") int id){
        try {
            Specification specification = specificationService.findOne(id);
            return new result(true,"成功",specification);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

    /*
     * 修改规格数据
     * */
    @RequestMapping("/update")
    public result update(@RequestBody Specification specification){
        try {
            specificationService.update(specification);
            return new result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

    /*
     * 修改规格数据
     * */
    @RequestMapping("/delete")
    public result delete(@RequestParam("ids") int[] ids){
        try {
            specificationService.delete(ids);
            return new result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

    @RequestMapping("/findSpecificationList")
    public result findSpecificationList(){
        try {
            //[{k,v},{k,v},{k,v}]
            List<tb_specification> tb_specificationList = specificationService.findAll();
            List<Map> list = new ArrayList<>();
            for ( tb_specification specification: tb_specificationList) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put("id",specification.getId());
                map.put("text",specification.getSpecName());
                list.add(map);
            }
            return  new result(true,"成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return  new result(false,"失败");
        }
    }

}
