package cn.he.Service.Impl;

import cn.he.Service.GoodsService;
import cn.he.domain.*;
import cn.he.group.Goods;
import cn.he.mapper.*;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private TbSellerMapper tbSellerMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "topic")
    private Destination destination;

    @Override
    public void save(Goods goods) {
        TbGoods tbGoods = goods.getTbGoods();
        tbGoodsMapper.insert(tbGoods);
        //此处需要获取插入的tbGoods的主键，在xml文件中配置
        TbGoodsDesc tbGoodsDesc = goods.getTbGoodsDesc();
        tbGoodsDesc.setGoodsId(tbGoods.getId());
        tbGoodsDescMapper.insert(tbGoodsDesc);
        List<TbItem> itemList = goods.getItemList();
        for (TbItem tbItem : itemList) {
            Brand brand = brandMapper.find(tbGoods.getBrandId());
            tbItem.setBrand(brand.getName());
            TbSeller seller = tbSellerMapper.findOne(tbGoods.getSellerId());
            tbItem.setSeller(seller.getName());
            tbItem.setSellerId(tbGoods.getSellerId());
            tbItem.setGoodsId(tbGoods.getId());
            tbItem.setCategory(tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName());
            tbItem.setCategoryid(tbGoods.getCategory3Id());
            tbItem.setUpdateTime(new Date());
            tbItem.setCreateTime(new Date());
            String itemImages = tbGoodsDesc.getItemImages();
            // [{"color":"白色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhV0oIFiAeRbcAADvSiWxu-s364.jpg"}]
            // 解析对象
            if(itemImages != null && !itemImages.isEmpty()){
                // 解析
                List<Map> mapList = JSON.parseArray(itemImages, Map.class);
                // 获取到第一个map集合
                Map map = mapList.get(0);
                // 通过key来获取值
                String url = (String) map.get("url");
                // 设置图片的地址
                tbItem.setImage(url);
            }

            // 先获取spu的名称，再获取到规格的名称，把spu名称 + 规格的名称
            String goodsName = tbGoods.getGoodsName();
            // {"机身内存":"32G","尺码":"170"}

            // 荣耀20 机身内容 尺码
            String spec = tbItem.getSpec();
            // {"机身内存":"32G","尺码":"170"}
            // 解析
            Map map = JSON.parseObject(spec, Map.class);
            // 遍历map集合
            for (Object o : map.keySet()) {
                // 拼接到一起
                goodsName += " " + o + " " + map.get(o);
            }
            // 设置标题
            tbItem.setTitle(goodsName);
            tbItemMapper.insert(tbItem);
        }
    }

    @Override
    public PageInfo<TbGoods> findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<TbGoods> tbGoods = tbGoodsMapper.selectByExample(null);
        return new PageInfo<>(tbGoods);
    }

    @Override
    public void updateGoods(String val, long[] ids) {
        for (final long id : ids) {
            TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
            tbGoods.setIsMarketable(val);
            tbGoodsMapper.updateByPrimaryKey(tbGoods);
            if ("1".equals(val)){
                jmsTemplate.send(destination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(id+"");
                    }
                });
            }
        }

    }
}

