package cn.he.service.impl;

import cn.he.domain.TbAddress;
import cn.he.domain.TbAddressExample;
import cn.he.mapper.TbAddressMapper;
import cn.he.service.AddressService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private TbAddressMapper tbAddressMapper;

    /**
     * 查询当前登录用户的收获地址
     * @param username
     * @return
     */
    @Override
    public List<TbAddress> findAddressList(String username) {
        TbAddressExample example = new TbAddressExample();
        TbAddressExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(username);
        return tbAddressMapper.selectByExample(example);
    }

}
