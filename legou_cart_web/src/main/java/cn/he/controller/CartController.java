package cn.he.controller;

import cn.he.entity.result;
import cn.he.group.Cart;
import cn.he.service.CartService;
import cn.he.utils.CookieUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

    /**
     * 查询购物车数据
     * @return
     */
    @RequestMapping("/findCartList")
    public result findCartList(){
        try {
            // 获取到HttpSession的id值
            // String sessionId = session.getId();

            // 获取到登陆名
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            // 从cookie中获取
            String sessionId = getSessionId();
            // 调用业务层，查询购物车数据
            List<Cart> cartList_session = cartService.findCartList(sessionId);
            // 判断是否登录
            if("anonymousUser".equals(username)){
                // 表示已经登录的状态
                return new result(true,"操作成功",cartList_session);
            }

            // 已经登录状态
            // 从redis中查询数据，key是username
            List<Cart> cartList_username = cartService.findCartList(username);

            // 判断
            if(cartList_session != null && cartList_session.size() > 0){
                // 数据的合并，把未登录状态下的数据合并到已经登录下
                cartList_username = cartService.mergeCartList(cartList_username,cartList_session);
                // 清除掉未登录状态下的redis的数据
                cartService.clearCartList(sessionId);
                // 保存
                cartService.saveRedis(username,cartList_username);
            }

            // 返回结果
            return new result(true,"操作成功",cartList_username);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"操作失败");
        }
    }

    /**
     * 如果未登录，redis的key是sessionId
     * 如果已经登录，redis的key是username
     * 添加购物车
     * @return
     */
    @RequestMapping("/addCart/{itemId}/{num}")
    // 解决跨域问题，js请求从http://localhost:8086服务器过来的
    @CrossOrigin("http://localhost:8086")
    public result addCart(@PathVariable("itemId") Long itemId, @PathVariable("num")int num){
        try {
            // 获取到HttpSession的id值
            // String sessionId = session.getId();

            // 从cookie中获取
            String sessionId = getSessionId();
            // 获取到登陆名
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            // 定义redis的key值
            String key = sessionId;
            // 判断
            if(!"anonymousUser".equals(username)){
                // 表示已经登录的状态
                key = username;
            }

            // 调用业务层，查询购物车数据
            List<Cart> cartList = cartService.findCartList(key);
            // 把购买的商品添加到购物车中
            cartList = cartService.saveCart(cartList,itemId,num);
            // 把购物车的数据存入到redis缓存中
            cartService.saveRedis(key,cartList);
            // 返回结果
            return new result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"操作失败");
        }
    }

    /**
     * 从cookie中获取到sessionId值
     * @return
     */
    public String getSessionId(){
        // 使用工具类
        String sessionId = CookieUtil.getCookieValue(request,"legou_sessionId","UTF-8");
        // 获取不到
        if(sessionId == null){
            // 从session中获取到sessionId
            sessionId = session.getId();
            // 存入到cookie
            CookieUtil.setCookie(request,response,"legou_sessionId",sessionId,48*60*60,"UTF-8");
        }
        return sessionId;
    }


}
