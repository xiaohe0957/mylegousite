package cn.he.controller;

import cn.he.domain.TbOrder;
import cn.he.entity.result;
import cn.he.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    /**
     * 保存订单
     * @param tbOrder
     * @return
     */
    @RequestMapping("/saveOrder")
    public result saveOrder(@RequestBody TbOrder tbOrder){
        try {
            // 获取登录名
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            // 设置给订单对象
            tbOrder.setUserId(username);
            orderService.saveOrder(tbOrder);
            return new result(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"保存失败");
        }
    }

}
