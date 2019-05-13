package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemCatService;

@Controller
public class TbItemCatController {
	@Resource
	private TbItemCatService tbItemCatServiceImpl;
	
	/**
	 * 查询出所有的菜单以及子菜单
	 * @param callback
	 * @return
	 */
	@RequestMapping("/rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue show(String callback) {
		MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatServiceImpl.gelAllMenu());
		mjv.setJsonpFunction(callback);
		return mjv;
	}
	
	
}
