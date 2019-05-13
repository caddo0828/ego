package com.ssm.manager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.commons.pojo.Result;
import com.ssm.manager.service.TbItemParamItemService;

@Controller
public class TbItemParamItemController {
	@Resource
	private TbItemParamItemService tbItemParamItemServiceImpl;
	
	@RequestMapping("/rest/item/param/item/query/{itemId}")
	@ResponseBody
	public Result selParamItem(@PathVariable long itemId) {
		return tbItemParamItemServiceImpl.selParamItem(itemId);
	}
}
