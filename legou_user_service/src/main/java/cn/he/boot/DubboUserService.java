package cn.he.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
public class DubboUserService {

    public static void main(String[] args) throws IOException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        // 线程阻塞
        System.in.read();
    }

}
