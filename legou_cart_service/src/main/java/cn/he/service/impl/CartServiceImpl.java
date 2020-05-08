package cn.he.service.impl;

import cn.he.domain.TbItem;
import cn.he.domain.TbOrderItem;
import cn.he.group.Cart;
import cn.he.mapper.TbItemMapper;
import cn.he.service.CartService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbItemMapper tbItemMapper;

    /**
     * 使用key值，从redis中查询购物车的数据
     * @param key
     * @return
     */
    @Override
    public List<Cart> findCartList(String key) {
        // 从redis查询购物车
        String cartListStr = (String) redisTemplate.boundValueOps(key).get();
        // 如果没有数据，获取到null
        if(cartListStr == null){
            // 空数组
            cartListStr = "[]";
        }
        // 把字符串转换集合
        List<Cart> cartList = JSON.parseArray(cartListStr, Cart.class);
        return cartList;
    }

    /**
     * 添加购物车
     * @param cartList
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public List<Cart> saveCart(List<Cart> cartList, Long itemId, int num) {
        // 通过购买商品获取到商家
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        // 获取到商家id和名称
        String sellerId = tbItem.getSellerId();
        String sellerName = tbItem.getSeller();

        // 判断cartList集合中是否有该商家
        Cart cart = sellerInCartList(cartList,sellerId);

        // 根据返回的返回，判断
        if(cart != null){
            // 有该商家
            List<TbOrderItem> orderItemList = cart.getOrderItemList();
            // 再判断购物车商家中是否包含该商品
            TbOrderItem tbOrderItem = itemIdInOrderItemList(orderItemList,itemId);
            // 进行判断
            if(tbOrderItem != null){
                // 存在，更新数量和总价格
                tbOrderItem.setNum(tbOrderItem.getNum() + num);

                // 判断当前的订单项的数量
                if(tbOrderItem.getNum() <= 0){
                    // 移除订单项
                    orderItemList.remove(tbOrderItem);
                    // 判断
                    if(orderItemList.size() <= 0){
                        cartList.remove(cart);
                    }
                }

                // 更新总价格
                double value = tbOrderItem.getTotalFee().doubleValue();
                // 设置新的价格
                tbOrderItem.setTotalFee(new BigDecimal(value + tbOrderItem.getPrice().doubleValue() * num));
            }else{
                // 不存在，把商品添加购物车订单项集合中
                // 创建新的订单项
                tbOrderItem = new TbOrderItem();
                tbOrderItem.setNum(num);
                tbOrderItem.setSellerId(sellerId);
                tbOrderItem.setTotalFee(new BigDecimal(tbItem.getPrice().doubleValue() * num));
                tbOrderItem.setTitle(tbItem.getTitle());
                tbOrderItem.setPrice(tbItem.getPrice());
                tbOrderItem.setPicPath(tbItem.getImage());
                tbOrderItem.setItemId(itemId);
                tbOrderItem.setGoodsId(tbItem.getGoodsId());
                // 添加到购物车的订单项的集合中
                orderItemList.add(tbOrderItem);
            }
        }else{
            // 没有该商家
            // 创建新的购物车对象，设置对应的数据
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(sellerName);
            // 订单项集合
            List<TbOrderItem> orderItemList = new ArrayList<>();
            // 创建订单项
            TbOrderItem tbOrderItem = new TbOrderItem();
            tbOrderItem.setNum(num);
            tbOrderItem.setSellerId(sellerId);
            tbOrderItem.setTotalFee(new BigDecimal(tbItem.getPrice().doubleValue() * num));
            tbOrderItem.setTitle(tbItem.getTitle());
            tbOrderItem.setPrice(tbItem.getPrice());
            tbOrderItem.setPicPath(tbItem.getImage());
            tbOrderItem.setItemId(itemId);
            tbOrderItem.setGoodsId(tbItem.getGoodsId());
            orderItemList.add(tbOrderItem);
            cart.setOrderItemList(orderItemList);
            // 把购物车对象存入到cartList集合
            cartList.add(cart);
        }
        return cartList;
    }

    /**
     * 再判断购物车商家中是否包含该商品
     * @param orderItemList
     * @param itemId
     * @return
     */
    private TbOrderItem itemIdInOrderItemList(List<TbOrderItem> orderItemList, Long itemId) {
        for (TbOrderItem tbOrderItem : orderItemList) {
            if(tbOrderItem.getItemId().longValue() == itemId){
                return tbOrderItem;
            }
        }
        return null;
    }

    /**
     * 判断cartList集合中是否有该商家
     * @param cartList
     * @param sellerId
     * @return
     */
    private Cart sellerInCartList(List<Cart> cartList, String sellerId) {
        // 遍历购物车集合
        for (Cart cart : cartList) {
            if(cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }

    /**
     * 保存到redis
     * @param sessionId
     * @param cartList
     */
    @Override
    public void saveRedis(String sessionId, List<Cart> cartList) {
        // 先把cartList转换成json的字符串
        String cartListStr = JSON.toJSONString(cartList);
        // 存入到redis
        redisTemplate.boundValueOps(sessionId).set(cartListStr,2, TimeUnit.DAYS);
    }

    /**
     * 合并数据
     * @param cartList_username
     * @param cartList_session
     * @return
     */
    @Override
    public List<Cart> mergeCartList(List<Cart> cartList_username, List<Cart> cartList_session) {
        // 遍历集合
        for (Cart cart : cartList_session) {
            // 获取到订单项
            for (TbOrderItem tbOrderItem : cart.getOrderItemList()) {
                // 把订单项，添加到cartList_username集合
                saveCart(cartList_username,tbOrderItem.getItemId(),tbOrderItem.getNum());
            }
        }
        return cartList_username;
    }

    /**
     * 清除数据
     * @param sessionId
     */
    @Override
    public void clearCartList(String sessionId) {
        redisTemplate.delete(sessionId);
    }

}
