package cn.he.service;

import java.util.Map;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
public interface PayService {

    Map<String,String> createNative(String userId) throws Exception;

    Map<String,String> queryPayStatus(String orderCode) throws Exception;

    void updatePayLogStatus(String userId, String transaction_id);
}
