package cn.he.controller;

import cn.he.Service.GoodsService;
import cn.he.domain.TbGoods;
import cn.he.entity.result;
import cn.he.group.Goods;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;
    @RequestMapping("/save")
    public result save(@RequestBody Goods goods){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            goods.getTbGoods().setSellerId(sellerId);
            goodsService.save(goods);
            return new result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public result findPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        try {
            PageInfo<TbGoods> page= goodsService.findPage(pageNum,pageSize);
            return new result(true,"成功",page.getTotal(),page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

    @RequestMapping("/updateGoods/{val}/{ids}")
    public result updateGoods(@PathVariable("val") String val,@PathVariable("ids") long[] ids){
        try {
            goodsService.updateGoods(val,ids);
            return new result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }

}
