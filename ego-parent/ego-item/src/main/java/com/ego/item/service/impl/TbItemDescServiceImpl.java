package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.item.service.TbItemDescService;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemDescServiceImpl implements TbItemDescService{
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.item.desc.key}")
	private String descKey;
	
	@Override
	public String selDescById(long id) {
		//先从缓存中判断是否存在数据
		String key = descKey+id;
		if(jedisDaoImpl.exists(key)) {
			String value = jedisDaoImpl.get(key);
			if(value!=null&&!value.equals("")) {
				return value;
			}
		}
		
		//原本不存在缓存数据时，将数据添加到缓存中
		String desc = tbItemDescDubboServiceImpl.selByItemId(id).getItemDesc();
		jedisDaoImpl.set(key, desc);
		
		return  desc;
	}

}
