package cn.he.controller;

import cn.he.entity.result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class indexController {
    @RequestMapping("/findLoginname")
    public result findLoginname(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return new result(true,"success",name);
    }
}
