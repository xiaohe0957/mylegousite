package cn.he.Service;

import cn.he.domain.TbSeller;
import com.github.pagehelper.PageInfo;


public interface SellerService {

    void save(TbSeller tbSeller);

    PageInfo<TbSeller> search(int pageNum, int pageSize, TbSeller tbSeller);

    TbSeller findOne(String sellerId);

    void auditing(String sellerId, String status);
}
