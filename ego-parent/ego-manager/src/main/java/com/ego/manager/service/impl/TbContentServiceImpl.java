package com.ego.manager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.common.pojo.Result;
import com.ego.common.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manager.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;

@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${jedis.picture.key}")
	private String key;
	
	
	@Override
	public EasyUIDataGrid selContentList(long categoryId, int page, int rows) {
		return tbContentDubboServiceImpl.selContentList(categoryId, page, rows);
	}

	
	@Override
	public Result delByIds(String ids) {
		Result result = new Result();
		String[] idsArray = ids.split(",");
		int index = 0;
		for (String id : idsArray) {
			try {
				index += tbContentDubboServiceImpl.delByIds(Long.parseLong(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(index==idsArray.length) {
			result.setStatus(200);
			//删除成功的时候，先判断redis中是否存在数据
			/**
			 * 根据id查询出对象，取出redis中的数据，删除对应的数据
			 */
			
		}else {
			result.setData("删除的数据中存在已经从数据库中移除的选项");
		}
		return result;
	}

	@Override
	public Result updContent(TbContent content) {
		Result result = new Result();
		//设置该内容被修改的时间
		Date date = new Date();
		content.setUpdated(date);
		try {
			int index = tbContentDubboServiceImpl.updContent(content);
			//先判断是否存在缓存数据
			if(index==1) {
				result.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 插入数据库的时候同时更新redis中的数据
	 */
	@Override
	public Result insContent(TbContent content) {
		Result result = new Result();
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		int index = 0;
		try {
			index = tbContentDubboServiceImpl.insContent(content);
			if(index>0) {
				result.setStatus(200);
				//只有当数据插入数据库成功时，才能对redis进行修改
				//判断redis中的数据
				if(jedisDaoImpl.exists(key)) {
					//取出数据
					String value = jedisDaoImpl.get(key);
					if(value!=null&&!value.equals("")) {
						//要将新的数据存储在redis中，因为前台要返回json数据，并且要将一个对象添加进去，必须将value转换成list集合
						List<Map> list = JsonUtils.jsonToList(value, Map.class);
						Map<String, Object> map = new HashMap<>();
						map.put("srcB",content.getPic2());
					    map.put("height", 240);
						map.put("alt","");
						map.put("width", 670);
						map.put("src", content.getPic());
						map.put("widthB", 550);
						map.put("href", content.getUrl());
						map.put("heightB", 240);
						
						//如果大广告中有六条数据，移除最后一条，在第一个位置，添加新的数据
						if(list.size()==6) {
							list.remove(5);
						}
						//在第一个位置添加入新的数据
						list.add(0,map);
						//将数据存储回redis中
						jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
