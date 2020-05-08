package cn.he.service.impl;

import cn.he.domain.TbGoods;
import cn.he.domain.TbGoodsDesc;
import cn.he.domain.TbItem;
import cn.he.domain.TbItemExample;
import cn.he.group.Goods;
import cn.he.mapper.TbGoodsDescMapper;
import cn.he.mapper.TbGoodsMapper;
import cn.he.mapper.TbItemCatMapper;
import cn.he.mapper.TbItemMapper;
import cn.he.service.ItempageService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
@Service
public class ItempageServiceImpl implements ItempageService {

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    /**
     * 查询Goods数据
     * @param goodsId
     * @return
     */
    @Override
    public Goods findByGoodsId(Long goodsId) {
        // 创建Goods对象，封装数据
        Goods goods = new Goods();
        // 先查询TbGoods数据
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsId);
        goods.setTbGoods(tbGoods);

        Map<String,String> categoryMap = new HashMap<>();
        categoryMap.put("category1",tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName());
        categoryMap.put("category2",tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName());
        categoryMap.put("category3",tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName());
        goods.setCategoryMap(categoryMap);

        // 设置desc描述数据
        TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
        goods.setTbGoodsDesc(tbGoodsDesc);

        // 查询sku数据
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        goods.setItemList(tbItems);

        return goods;
    }

    @Override
    public List<Goods> findAll() {
        List<Goods> goodsList = new ArrayList<>();
        List<TbGoods> tbGoods = tbGoodsMapper.selectAll();
        for (TbGoods tbGood : tbGoods) {
            Goods byGoodsId = findByGoodsId(tbGood.getId());
            goodsList.add(byGoodsId);
        }
        return goodsList;
    }


}
