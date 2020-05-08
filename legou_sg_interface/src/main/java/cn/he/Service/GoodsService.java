package cn.he.Service;

import cn.he.domain.TbGoods;
import cn.he.group.Goods;
import com.github.pagehelper.PageInfo;

public interface GoodsService {
    void save(Goods goods);

    PageInfo<TbGoods> findPage(int pageNum, int pageSize);

    void updateGoods(String val, long[] ids);
}
