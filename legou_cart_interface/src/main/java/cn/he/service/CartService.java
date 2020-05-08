package cn.he.service;

import cn.he.group.Cart;

import java.util.List;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
public interface CartService {

    List<Cart> findCartList(String key);

    List<Cart> saveCart(List<Cart> cartList, Long itemId, int num);

    void saveRedis(String sessionId, List<Cart> cartList);

    List<Cart> mergeCartList(List<Cart> cartList_username, List<Cart> cartList_session);

    void clearCartList(String sessionId);
}
