package cn.he.controller;

import cn.he.Service.SellerService;
import cn.he.domain.TbSeller;
import cn.he.entity.result;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    @RequestMapping("/save")
    public result save(@RequestBody TbSeller tbSeller){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            tbSeller.setPassword(passwordEncoder.encode(tbSeller.getPassword()));
            sellerService.save(tbSeller);
            return new result(true,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"fail");
        }

    }

//    findLoginName

    @RequestMapping("/findLoginName")
    public result findLoginName(){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            return new result(true,"success",name);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"fail");
        }

    }
}
