package com.ssm.manager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.commons.pojo.Result;
import com.ssm.manager.service.TbItemDescService;

@Controller
public class TbItemDescController {
	@Resource
	private TbItemDescService tbItemDescServiceImpl;
	
	@RequestMapping("/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public Result selDescById(@PathVariable long itemId) {
		return tbItemDescServiceImpl.selDescById(itemId);
	}
	
}
