package cn.he.controller;

import cn.he.domain.TbItem;
import cn.he.group.Goods;
import cn.he.service.ItempageService;
import com.alibaba.dubbo.config.annotation.Reference;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class itemMessageListener implements MessageListener {
    @Reference
    private ItempageService itempageService;
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            long id = Long.parseLong(textMessage.getText());
            Goods byGoodsId = itempageService.findByGoodsId(id);
            List<TbItem> itemList = byGoodsId.getItemList();
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            for (TbItem tbItem : itemList) {
                Map<String,Object> map = new HashMap<>();
                map.put("goods",byGoodsId);
                map.put("tbItem",tbItem);
                FileWriter writer = new FileWriter("E:\\JavaEE\\legou_parent_html\\" + tbItem.getId() + ".html");
                template.process(map,writer);
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
