package com.ego.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.redis.dao.JedisDao;
import com.ego.redis.dao.impl.JedisDaoImpl;

@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	@Resource
	private JedisDao JedisDaoImpl;
	@Value("${jedis.picture.key}")
	private String key;
	
	@Override
	public String selByCount() {
		//先判断是否存在对应的key，存在对应的数据
		if(JedisDaoImpl.exists(key)) {
			String value = JedisDaoImpl.get(key);
			//取出来判断是否为空数据
			if(value!=null&&!value.equals("")) {
				return value;
			}
		}else {
			//自己设置查询前六个大广告数据，并且按排序方式进行显示
			List<TbContent> contentList = tbContentDubboServiceImpl.selByCount(6, true);
			List<Map<String,Object>> dataList  = new ArrayList<>();
		    for (TbContent content : contentList) {
				Map<String, Object> map = new HashMap<>();
				map.put("srcB",content.getPic2());
			    map.put("height", 240);
				map.put("alt","");
				map.put("width", 670);
				map.put("src", content.getPic());
				map.put("widthB", 550);
				map.put("href", content.getUrl());
				map.put("heightB", 240);
				dataList.add(map);
			}
		    //将数据存储在缓存中
		    String data = JsonUtils.objectToJson(dataList);
		    JedisDaoImpl.set(key, data);
		   
		    //将数据转换成json格式回送给前台
			return JsonUtils.objectToJson(data);
		}			
	    return null;
  
	}

}
