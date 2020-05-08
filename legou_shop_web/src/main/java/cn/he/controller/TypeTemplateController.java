package cn.he.controller;

import cn.he.Service.TypeTemplateService;
import cn.he.domain.TbTypeTemplate;
import cn.he.entity.result;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * controller
 *
 * @author Administrator
 * <p>
 * 模板管理模块
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public result findAll() {
        try {
            List<TbTypeTemplate> list = typeTemplateService.findAll();
            return new result(true, "查询成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false, "查询失败");
        }
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
            PageInfo<TbTypeTemplate> page = typeTemplateService.findPage(pageNum, pageSize);
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
     * @param typeTemplate
     * @return
     */
    @RequestMapping("/add")
    public result add(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.add(typeTemplate);
            return new result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param typeTemplate
     * @return
     */
    @RequestMapping("/update")
    public result update(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.update(typeTemplate);
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
            TbTypeTemplate typeTemplate = typeTemplateService.findOne(id);
            return new result(true, "查询成功", typeTemplate);
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
            typeTemplateService.delete(ids);
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
    public result search(@RequestBody TbTypeTemplate typeTemplate, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            // 分页条件查询
            PageInfo<TbTypeTemplate> page = typeTemplateService.findPage(typeTemplate, pageNum, pageSize);
            return new result(true, "查询成功", page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false, "查询失败");
        }
    }

    @RequestMapping("/findSpecList/{id}")
    public result findSpecList(@PathVariable("id") Long id) {
        try {
            List<Map> list = typeTemplateService.findSpecList(id);
            return new result(true, "查询成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false, "查询失败");
        }
    }

}
