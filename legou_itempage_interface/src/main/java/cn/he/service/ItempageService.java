package cn.he.service;

import cn.he.group.Goods;

import java.util.List;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
public interface ItempageService {

    Goods findByGoodsId(Long goodsId);

    List<Goods> findAll();
}
