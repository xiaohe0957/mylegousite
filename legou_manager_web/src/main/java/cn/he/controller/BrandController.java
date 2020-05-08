package cn.he.controller;

import cn.he.Service.BrandService;
import cn.he.domain.Brand;
import cn.he.entity.result;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Brand")
public class BrandController {
    @Reference
    private BrandService brandService;

    /*
    * 查询所有品牌
    * */
    @RequestMapping("/findAll")
    public List<Brand> findAll(){
        return brandService.findAll();
    }

    /*
    * 分页查询品牌
    * */
    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public result findPage(@PathVariable("pageNum") int num, @PathVariable("pageSize") int size){
        try {
            PageInfo<Brand> page = brandService.findPage(num,size);
            return new result(true,"成功",page.getTotal(),page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

    /*
     * 新增品牌
     * */
    @RequestMapping("/save")
    public result save(@RequestBody Brand brand){
        try {
            brandService.save(brand);
            return  new result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return  new result(false,"失败");
        }
    }

    /*
     * 分页查询品牌
     * */
    @RequestMapping("/findOne/{id}")
    public result findOne(@PathVariable("id") int id){
        try {
            Brand brand = brandService.findOne(id);
            return new result(true,"成功",brand);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

    /*
     * 修改品牌
     * */
    @RequestMapping("/update")
    public result update(@RequestBody Brand brand){
        try {
            brandService.update(brand);
            return  new result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return  new result(false,"失败");
        }
    }

    /*
     * 修改品牌
     * */
    @RequestMapping("/delete/{ids}")
    public result delete(@PathVariable("ids") int[] ids){
        try {
            brandService.delete(ids);
            return  new result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return  new result(false,"失败");
        }
    }

    @RequestMapping("/findBrandList")
    public result findBrandList(){
        try {
            //[{k,v},{k,v},{k,v}]
            List<Brand> brandList = brandService.findAll();
            List<Map> list = new ArrayList<>();
            for (Brand brand : brandList) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put("id",brand.getId());
                map.put("text",brand.getName());
                list.add(map);
            }
            return  new result(true,"成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return  new result(false,"失败");
        }
    }
}
