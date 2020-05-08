package cn.he.service;

import cn.he.domain.TbUser;
import cn.he.entity.result;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
public interface UserService {

    String sendCode(String phone) throws Exception;

    result regist(TbUser tbUser, String code);
}
