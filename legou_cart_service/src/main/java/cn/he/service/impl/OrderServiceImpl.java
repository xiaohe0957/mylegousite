package cn.he.service.impl;

import cn.he.domain.TbOrder;
import cn.he.domain.TbOrderItem;
import cn.he.group.Cart;
import cn.he.mapper.TbOrderItemMapper;
import cn.he.mapper.TbOrderMapper;
import cn.he.service.OrderService;
import cn.he.utils.IdWorker;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    /**
     * 保存订单
     * @param tbOrder
     */
    @Override
    public void saveOrder(TbOrder tbOrder) {
        // 先获取购物车数据
        String cartListStr = (String) redisTemplate.boundValueOps(tbOrder.getUserId()).get();
        // 转换
        List<Cart> cartList = JSON.parseArray(cartListStr, Cart.class);
        // 遍历购物车集合
        for (Cart cart : cartList) {
            // 一个cart，一个订单（包含多个订单项）
            // 创建订单对象
            TbOrder order = new TbOrder();
            order.setUserId(tbOrder.getUserId());
            // 通过雪花算法生成的唯一的订单的编号
            long orderId = idWorker.nextId();
            // 订单的编号，数字，唯一的值
            order.setOrderId(orderId);
            // 支付类型
            order.setPaymentType(tbOrder.getPaymentType());
            // //状态：未付款
            order.setStatus("1");
            // 订单创建日期
            order.setCreateTime(new Date());
            // 订单更新日期
            order.setUpdateTime(new Date());
            // 地址
            order.setReceiverAreaName(tbOrder.getReceiverAreaName());
            // 手机号
            order.setReceiverMobile(tbOrder.getReceiverMobile());
            // 收货人
            order.setReceiver(tbOrder.getReceiver());
            // 订单来源
            order.setSourceType(tbOrder.getSourceType());
            // 商家ID
            order.setSellerId(cart.getSellerId());

            // 定义总金额
            double money = 0d;

            // ========================================
            // 处理订单项数据
            for (TbOrderItem tbOrderItem : cart.getOrderItemList()) {
                // 给订单项再设置数据
                tbOrderItem.setId(idWorker.nextId());
                tbOrderItem.setOrderId(orderId);

                // 累加订单项的金额
                money += tbOrderItem.getTotalFee().doubleValue();

                // 保存订单项
                tbOrderItemMapper.insert(tbOrderItem);
            }

            // 设置订单的总金额 = 所有的订单项组成
            order.setPayment(new BigDecimal(money));
            // 保存订单
            tbOrderMapper.insert(order);
        }
        // 从redis中移除购物车的数据
        redisTemplate.delete(tbOrder.getUserId());
    }

}
