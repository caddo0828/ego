package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemParamItemService;

@Controller
public class TbItemParamItemController {
	@Resource
	private TbItemParamItemService tbItemParamItemServiceImpl;
	
	/**
	 * AJAX伪静态查询商品规格参数
	 * @return
	 */
	@RequestMapping(value="/item/param/{id}.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String showParamItem(@PathVariable long id) {
		return tbItemParamItemServiceImpl.selParamByItemId(id);
	}
	
	
}
