package cn.he.controller;

import cn.he.domain.TbItemCat;
import cn.he.entity.result;
import cn.he.Service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 *
 * @author Administrator
 * <p>
 * 商品分类模块
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbItemCat> findAll() {
        return itemCatService.findAll();
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public result findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            // 调用业务层，查询
            PageInfo<TbItemCat> page = itemCatService.findPage(pageNum, pageSize);
            return new result(true, "查询成功", page.getTotal(), page.getList());
        } catch (Exception e) {
            // 打印异常
            e.printStackTrace();
            // 返回错误提示信息
            return new result(false, "查询失败");
        }
    }

    /**
     * 增加
     *
     * @param itemCat
     * @return
     */
    @RequestMapping("/add")
    public result add(@RequestBody TbItemCat itemCat) {
        try {
            itemCatService.add(itemCat);
            return new result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param itemCat
     * @return
     */
    @RequestMapping("/update")
    public result update(@RequestBody TbItemCat itemCat) {
        try {
            itemCatService.update(itemCat);
            return new result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne/{id}")
    public result findOne(@PathVariable("id") Long id) {
        try {
            // 通过主键查询
            TbItemCat itemCat = itemCatService.findOne(id);
            return new result(true, "查询成功", itemCat);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false, "查询失败");
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete/{ids}")
    public result delete(@PathVariable("ids") Long[] ids) {
        try {
            itemCatService.delete(ids);
            return new result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param
     * @param
     * @param
     * @return
     */

    @RequestMapping("/search/{pageNum}/{pageSize}")
    public result search(@RequestBody TbItemCat itemCat, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            // 调用业务层，查询
            PageInfo<TbItemCat> page = itemCatService.findPage(itemCat, pageNum, pageSize);
            return new result(true, "查询成功", page.getTotal(), page.getList());
        } catch (Exception e) {
            // 打印异常
            e.printStackTrace();
            // 返回错误提示信息
            return new result(false, "查询失败");
        }
    }



    /*
    * 查询一级分类
    * */
    @RequestMapping("/findByParentId/{id}")
    public result findByParentId (@PathVariable("id") int id) {
        try {
            List<TbItemCat> list = itemCatService.findByParentId(id);
            return new result(true, "增加成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false, "增加失败");
        }
    }

}
