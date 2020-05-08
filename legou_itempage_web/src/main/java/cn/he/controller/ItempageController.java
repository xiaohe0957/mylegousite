package cn.he.controller;

import cn.he.domain.TbItem;
import cn.he.entity.result;
import cn.he.group.Goods;
import cn.he.service.ItempageService;
import com.alibaba.dubbo.config.annotation.Reference;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
@RestController
@RequestMapping("/itempage")
public class ItempageController {

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Reference
    private ItempageService itempageService;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/genHtmlByGoodsId/{goodsId}")
    public result genHtmlByGoodsId(@PathVariable("goodsId") Long goodsId){
        try {
            // 调用业务层
            Goods goods = itempageService.findByGoodsId(goodsId);
            // 生成商品的详情页
            Configuration configuration = freeMarkerConfig.getConfiguration();
            // 获取模板对象
            Template template = configuration.getTemplate("item.ftl");

            // 获取到sku集合，遍历集合
            List<TbItem> itemList = goods.getItemList();
            // 遍历
            for (TbItem tbItem : itemList) {
                // 生成详情页
                // 创建输出流
                //E:\JavaEE\legou_parent_html
                FileWriter writer = new FileWriter(new File("E:\\JavaEE\\legou_parent_html\\"+tbItem.getId()+".html"));
                // 创建map，用来存储数据
                Map map = new HashMap();
                map.put("goods",goods);
                map.put("tbItem",tbItem);
                template.process(map,writer);
                // 关闭流
                writer.flush();
                writer.close();
            }
            return new result(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"保存失败");
        }
    }

    @RequestMapping("/genAllHtml")
    public result genAllHtml(){
        try {
            // 查询goods数据
            List<Goods> goodsList = itempageService.findAll();
            for (Goods goods : goodsList) {
                // 获取sku数据
                List<TbItem> itemList = goods.getItemList();
                // 创建配置
                Configuration configuration = freeMarkerConfig.getConfiguration();

                // 获取模板
                Template template = configuration.getTemplate("item.ftl");

                // 遍历 sku数据
                for (TbItem tbItem : itemList) {
                    // 创建map集合，存储数据
                    Map<String,Object> map = new HashMap<>();
                    map.put("goods",goods);
                    map.put("tbItem",tbItem);
                    // 创建输出流
                    FileWriter writer = new FileWriter("E:\\JavaEE\\legou_parent_html\\"+tbItem.getId()+".html");
                    // 生成html
                    template.process(map,writer);
                    // 关闭流
                    writer.flush();
                    writer.close();

                }
            }
            // 返回结果
            return new result(true,"生成成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"生成失败");
        }
    }

}
