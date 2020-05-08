package cn.he.controller;

import cn.he.Service.SellerService;
import cn.he.domain.TbSeller;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//@Component("detailService")
public class userDetailsServiceImpl implements UserDetailsService {

    @Reference
    private SellerService sellerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username == null || username.trim().isEmpty()){
            // 给SpringSecurity框架返回 null
            return null;
        }
        // 查询数据库
        TbSeller tbSeller = sellerService.findOne(username);
        // 包含账号 密码 信息...
        // 数据校验
        if(tbSeller == null){
            //
            return null;
        }

        // 判断商家是否是已经审核通过
        if(!tbSeller.getStatus().equals("1")){
            return null;
        }
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User(username,tbSeller.getPassword(),list);
        return user;
    }
}
