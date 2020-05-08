package cn.he.service.impl;

import cn.he.domain.TbUser;
import cn.he.entity.result;
import cn.he.mapper.TbUserMapper;
import cn.he.service.UserService;
import cn.he.utils.HttpClient;
import cn.he.utils.RandomCode;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.PatternSyntaxException;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbUserMapper tbUserMapper;

    /**
     * 发送短信验证码
     * @param phone
     */
    @Override
    public String sendCode(String phone) throws Exception {
        // 生成验证码
        String code = RandomCode.genCode();
        // 存入到redis中  key是手机号 值是code  设置5分钟后过期
        redisTemplate.boundValueOps(phone).set(code,5,TimeUnit.MINUTES);

        // 调用短信服务接口，发送短信
        HttpClient client = new HttpClient("http://localhost:8087/sms/send/" + phone + "/" + code);
        // 发送请求
        client.get();
        // 获取结果
        return client.getContent();
    }

    /**
     * 用户注册功能
     * @param tbUser
     */
    @Override
    public result regist(TbUser tbUser,String code) {

        String redisCode = (String) redisTemplate.boundValueOps(tbUser.getPhone()).get();
        // 判断验证码输入是否正确
        if (code==null || !code.equals(redisCode)){
            return new result(false,"验证码错误");
        }
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        String password = DigestUtils.md5Hex(tbUser.getPassword());
        tbUser.setPassword(password);
        tbUserMapper.insert(tbUser);
        return new result(true,"注册成功");
    }

}
