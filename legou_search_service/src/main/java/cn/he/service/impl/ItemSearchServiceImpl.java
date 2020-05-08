package cn.he.service.impl;

import cn.he.domain.TbItem;
import cn.he.domain.TbItemCat;
import cn.he.domain.TbItemCatExample;
import cn.he.domain.TbTypeTemplate;
import cn.he.mapper.TbItemCatMapper;
import cn.he.mapper.TbTypeTemplateMapper;
import cn.he.service.ItemSearchService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拓薪教育 -- 腾讯课程认证机构
 * 樱木老师
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map search(Map paramMap) {
        Map map = new HashMap<>();
        String keyword = (String) paramMap.get("keyword");
        Query query = new SimpleQuery();
        query.addCriteria(new Criteria("item_keywords").is(keyword));
        String brand = (String) paramMap.get("brand");
        // 判断
        if(brand != null && !brand.isEmpty()){
            // 选择了品牌，设置过滤查询
            // 创建过滤查询对象
            FilterQuery filterQuery = new SimpleFilterQuery();
            // 添加过滤查询的条件
            filterQuery.addCriteria(new Criteria("item_brand").is(brand));
            // 添加过滤查询
            query.addFilterQuery(filterQuery);
        }
        String category = (String) paramMap.get("category");
        // 判断
        if(category != null && !category.isEmpty()){
            // 选择了品牌，设置过滤查询
            // 创建过滤查询对象
            FilterQuery filterQuery = new SimpleFilterQuery();
            // 添加过滤查询的条件
            filterQuery.addCriteria(new Criteria("item_category").is(category));
            // 添加过滤查询
            query.addFilterQuery(filterQuery);
        }
        int pageNum = (int) paramMap.get("page");
        // 起始位置
        query.setOffset((pageNum - 1)*60);
        // 每页显示条数
        query.setRows(60);

        // 带主条件分页查询
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
        // 封装数据
        map.put("rows",page.getContent());
        // 添加属性
        map.put("totalPage",page.getTotalPages());
        map.put("total",page.getTotalElements());

        Query categoryQuery = new SimpleQuery();
        categoryQuery.addCriteria(new Criteria("item_keywords").is(keyword));
        GroupOptions groupOptions = new GroupOptions();
        groupOptions.addGroupByField("item_category");
        categoryQuery.setGroupOptions(groupOptions);
        GroupPage<TbItem> tbItems = solrTemplate.queryForGroupPage(categoryQuery, TbItem.class);
        ArrayList<String> list = new ArrayList<>();
        GroupResult<TbItem> item_category = tbItems.getGroupResult("item_category");
        Page<GroupEntry<TbItem>> groupEntries = item_category.getGroupEntries();
        for (GroupEntry<TbItem> groupEntry : groupEntries) {
            String groupValue = groupEntry.getGroupValue();
            list.add(groupValue);
        }
        map.put("categoryList",list);

        Query groupQueryBrand = new SimpleQuery();
        // 添加查询的条件
        groupQueryBrand.addCriteria(new Criteria("item_keywords").is(keyword));

        // 创建分组
        GroupOptions groupOptionsBrand = new GroupOptions();
        groupOptionsBrand.addGroupByField("item_brand");
        // 设置分组的查询
        groupQueryBrand.setGroupOptions(groupOptionsBrand);
        // 执行查询
        GroupPage<TbItem> groupPageBrand = solrTemplate.queryForGroupPage(groupQueryBrand, TbItem.class);

        // 创建List集合，存储查询的组的名称
        List<String> brandList = new ArrayList<>();

        // 下面solrTempate封装的一段API
        GroupResult<TbItem> item_brand = groupPageBrand.getGroupResult("item_brand");
        Page<GroupEntry<TbItem>> groupEntriesBrand = item_brand.getGroupEntries();
        for (GroupEntry<TbItem> tbItemGroupEntry : groupEntriesBrand.getContent()) {
            String v = tbItemGroupEntry.getGroupValue();
            brandList.add(v);
        }
        // 存入到map集合
        map.put("brandList",brandList);
        return map;
    }
}
