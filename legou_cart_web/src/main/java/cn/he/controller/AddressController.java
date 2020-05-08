package cn.he.controller;

import cn.he.domain.TbAddress;
import cn.he.entity.result;
import cn.he.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    /**
     * 查询登录用户的收获地址
     * @return
     */
    @RequestMapping("/findAddressList")
    public result findAddressList(){
        try {
            // 获取到登陆用户名
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            // 调用业务层
            List<TbAddress> addressList = addressService.findAddressList(username);
            return new result(true,"查询成功",addressList);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"查询失败");
        }
    }

}
