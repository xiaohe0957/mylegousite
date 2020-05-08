package cn.he.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DubboSgService {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        System.in.read();
    }
}
