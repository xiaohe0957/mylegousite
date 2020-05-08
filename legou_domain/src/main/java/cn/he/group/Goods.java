package cn.he.group;

import cn.he.domain.TbGoods;
import cn.he.domain.TbGoodsDesc;
import cn.he.domain.TbItem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Goods implements Serializable {
    private TbGoods tbGoods;
    private TbGoodsDesc tbGoodsDesc;
    private List<TbItem> itemList;
    private Map<String, String> categoryMap;

    public Map<String, String> getCategoryMap() {
        return categoryMap;
    }

    public TbGoods getTbGoods() {
        return tbGoods;
    }

    public void setTbGoods(TbGoods tbGoods) {
        this.tbGoods = tbGoods;
    }

    public TbGoodsDesc getTbGoodsDesc() {
        return tbGoodsDesc;
    }

    public void setTbGoodsDesc(TbGoodsDesc tbGoodsDesc) {
        this.tbGoodsDesc = tbGoodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }

    public void setCategoryMap(Map<String, String> categoryMap) {
    }
}
