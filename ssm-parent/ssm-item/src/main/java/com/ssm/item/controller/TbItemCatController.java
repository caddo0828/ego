package com.ssm.item.controller;


import javax.annotation.Resource;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.item.service.TbItemCatService;

@Controller
public class TbItemCatController {
	@Resource
	private TbItemCatService tbItemCatServiceImpl;
		
	@RequestMapping("/rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue showCatList(String callback) {
		MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatServiceImpl.getAllMenu());
		mjv.setJsonpFunction(callback);
		//mjv对象返回的形式是     函数名（参数）;  并且将参数转换成json数据
		return mjv;
	}
	
}
