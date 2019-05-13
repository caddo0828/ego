package com.ssm.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.commons.utils.JsonUtils;
import com.ssm.dubbo.service.TbContentDubboService;
import com.ssm.pojo.TbContent;
import com.ssm.portal.service.TbContentService;

@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	
	@Override
	public String picContent() {
		List<Map<String, Object>> picList = new ArrayList<>();
		List<TbContent> contentList = tbContentDubboServiceImpl.selPicList(6, true);
		for (TbContent tbContent : contentList) {
			Map<String, Object> map = new HashMap<>();
			map.put("srcB", tbContent.getPic2());
			map.put("height", 240);
			map.put("alt", "");
			map.put("width", 670);
			map.put("src", tbContent.getPic());
			map.put("widthB", 550);
			map.put("href", tbContent.getUrl());
			map.put("heightB", 240);
			picList.add(map);
		}
		//前台要的是一个json数据，将集合转json数据
		
		return JsonUtils.objectToJson(picList);
	}

}
