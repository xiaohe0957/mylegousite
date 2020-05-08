package cn.he.Service.Impl;

import cn.he.Service.SellerService;
import cn.he.domain.TbSeller;
import cn.he.mapper.TbSellerMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    private TbSellerMapper sellerMapper;

    @Override
    public void save(TbSeller tbSeller) {
        tbSeller.setStatus("0");
        tbSeller.setCreateTime(new Date());
        sellerMapper.insert(tbSeller);
    }

    @Override
    public PageInfo<TbSeller> search(int pageNum, int pageSize, TbSeller tbSeller) {
        PageHelper.startPage(pageNum,pageSize);
        List<TbSeller> list = sellerMapper.searchAll(tbSeller);
        return new PageInfo<>(list);
    }

    @Override
    public TbSeller findOne(String sellerId) {
        return sellerMapper.findOne(sellerId);
    }

    @Override
    public void auditing(String sellerId, String status) {
//        TbSeller tbSeller = sellerMapper.findOne(sellerId);
//        tbSeller.setStatus(status);
        sellerMapper.auditing(sellerId,status);
    }
}
