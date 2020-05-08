package cn.he.service.impl;



import cn.he.domain.TbItem;
import cn.he.domain.TbItemExample;
import cn.he.mapper.TbItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

public class solrMessageListener implements MessageListener {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            long tbGoodsId = Long.parseLong(textMessage.getText());
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(tbGoodsId);
            List<TbItem> tbItems = tbItemMapper.selectByExample(example);
            solrTemplate.saveBeans(tbItems);
            solrTemplate.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
